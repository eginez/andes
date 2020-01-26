package xyz.eginez.andes.annotations;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("xyz.eginez.andes.annotations.SSAOperation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SSAOperationProcessor extends AbstractProcessor {
    public SSAOperationProcessor() {}

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elems = roundEnv.getElementsAnnotatedWith(SSAOperation.class);
        try {
            List<TypeElement> collect = elems
                    .stream()
                    .filter(x -> x.getKind().isClass())
                    .map(x -> (TypeElement) x)
                    .collect(Collectors.toList());
            StringBuilder operationsMap = new StringBuilder();
            StringBuilder imports = new StringBuilder();

            for (TypeElement el : collect) {
                Name name = el.getSimpleName();
                operationsMap.append(String.format("ALL_OPERATIONS.put(\"%s\", new %s());\n", name, name));
                imports.append(String.format("import xyz.eginez.andes.nodes.%s;\n", name));
            }

            writeFile(imports.toString(), operationsMap.toString());

        } catch (FilerException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "File has already been created");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void writeFile(String imports, String operationsMap) throws IOException {
        List<String> statements = Arrays.asList(
                "package xyz.eginez.andes;",
                imports,
                "import java.util.HashMap;",
                "import java.util.Map;",
                "public class AllOperations {",
                "public static final Map<String, Operation> ALL_OPERATIONS = new HashMap<>();",
                "static {",
                operationsMap,
                "}",
                "}"
        );
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile("xyz.eginez.andes.AllOperations");
        PrintWriter pw = new PrintWriter(sourceFile.openWriter());
        String text = String.join("\n", statements);
        pw.print(text);
        pw.flush();
        pw.close();
    }
}
