package info.unterstein;

import org.jooby.Jooby;

/**
 * @author jooby generator
 */
public class CheckoutService extends Jooby {

  {
    get("/", () -> "Hi checkout!");
  }

  public static void main(final String[] args) throws Exception {
    run(CheckoutService::new, args);
  }

}
