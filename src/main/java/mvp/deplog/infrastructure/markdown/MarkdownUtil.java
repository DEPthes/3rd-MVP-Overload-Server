package mvp.deplog.infrastructure.markdown;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MarkdownUtil {

    private static final String EMPTY_STRING = "";

    // Markdown -> (Html) -> Text
    public static String extractPlainText(String markdown) {
        String html = markdownToHtml(markdown);
        String text = Jsoup.parse(html).text();

        return text.isEmpty() ? EMPTY_STRING : text;
    }

    // Markdown -> (Html) -> ImageUrl list
    public static List<String> extractImageLinks(String markdown) {
        String html = markdownToHtml(markdown);

        Document document = Jsoup.parse(html);
        Elements imgElements = document.select("img");
        List<String> imageLinks = new ArrayList<>();
        for (Element imgElement : imgElements) {
            imageLinks.add(imgElement.attr("src"));
        }
        return imageLinks;
    }

    // Markdown -> (Html) -> Text -> Preview Content
    public static String extractPreviewContent(String content) {
        if (content == null || content.isEmpty())
            return EMPTY_STRING;

        String plainText = extractPlainText(content);
        if (plainText.length() > 100)
            return plainText.substring(0, 100);
         else
             return plainText;
    }

    // Markdown -> (Html) -> First ImageUrl
    public static String extractPreviewImage(String markdown) {
        String html = markdownToHtml(markdown);

        Document document = Jsoup.parse(html);
        Elements imgElements = document.select("img");

        return imgElements.isEmpty() ? EMPTY_STRING : imgElements.get(0).attr("src");
    }

    // Markdown -> Html
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
