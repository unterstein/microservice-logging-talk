package info.unterstein;

import info.unterstein.model.Article;
import info.unterstein.model.ArticleCheckout;
import info.unterstein.model.Articles;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.json.Gzon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
public class ArticleService extends Jooby {

  private static final Logger log = LoggerFactory.getLogger(ArticleService.class);

  private Map<Long, Article> articles = initialData();

  {
    use(new Gzon());

    before((req, rsp) -> {
      ServiceCommons.beforeRequest(req);
    });

    get("/", (req, rsp) -> {
      rsp.send(new Articles(articles.values()));
    }).produces(MediaType.json);

    get("/:id", (req, rsp) -> {
      rsp.send(articles.get(req.param("id").longValue()));
    }).produces(MediaType.json);

    post("/checkout/:id", (req, rsp) -> {
      ArticleCheckout articleCheckout = req.body().to(ArticleCheckout.class);
      Article article = articles.get(articleCheckout.id);

      if (article != null && article.inStock >= articleCheckout.quantity) {
        article.inStock -= articleCheckout.quantity;
        rsp.send(ServerAnswer.ok());
      } else {
        rsp.send(ServerAnswer.failure());
      }
    }).produces(MediaType.json);

    after((req, rsp, result) -> {
      ServiceCommons.afterRequest(req);
      return result;
    });
  }

  public static void main(final String[] args) throws Exception {
    run(ArticleService::new, args);
  }

  private static Map<Long, Article> initialData() {
    Map<Long, Article> result = new HashMap<>();
    result.put(1L, new Article(1L, "Flens", 1.19, 100));
    result.put(2L, new Article(2L, "Becks", 1.30, 100));
    result.put(3L, new Article(3L, "Astra", 1.00, 100));
    result.put(4L, new Article(4L, "Eschweger", 1.10, 100));
    result.put(5L, new Article(5L, "Störtebecker", 1.50, 100));
    result.put(6L, new Article(6L, "Bitburger", 1.40, 100));
    result.put(6L, new Article(6L, "Hacker Pschorr", 1.50, 100));
    result.put(6L, new Article(6L, "Paulaner", 1.49, 100));
    return result;
  }

}
