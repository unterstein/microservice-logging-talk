package info.unterstein.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
public class Checkout {

  public CheckoutCustomer customer;

  public List<CheckoutArticle> articles = new ArrayList<>();

}
