import annotation.HtmlForm;
import annotation.HtmlInput;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("annotation.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            System.out.println("Processing element: " + element.getSimpleName());
            if (htmlForm != null) {
                String fileName = htmlForm.fileName();
                String action = htmlForm.action();
                String method = htmlForm.method();
                StringBuilder htmlBuilder = new StringBuilder();
                htmlBuilder.append("<form action=\"")
                        .append(action)
                        .append("\" method=\"")
                        .append(method)
                        .append("\">\n");
                for (Element field : element.getEnclosedElements()) {
                    HtmlInput htmlInput = field.getAnnotation(HtmlInput.class);
                    if (htmlInput != null) {
                        htmlBuilder.append("    <input type=\"")
                                .append(htmlInput.type())
                                .append("\" name=\"")
                                .append(htmlInput.name())
                                .append("\" placeholder=\"")
                                .append(htmlInput.placeholder())
                                .append("\">\n");
                    }
                }
                htmlBuilder.append("    <input type=\"submit\" value=\"Send\">\n")
                        .append("</form>");
                try {
                    FileObject file = processingEnv.getFiler().createResource(
                            StandardLocation.CLASS_OUTPUT,
                            "",
                            fileName
                    );
                    try (BufferedWriter writer = new BufferedWriter(file.openWriter())) {
                        writer.write(htmlBuilder.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
