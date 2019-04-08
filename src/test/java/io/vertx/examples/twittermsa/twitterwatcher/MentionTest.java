package io.vertx.examples.twittermsa.twitterwatcher;

import io.vertx.core.json.Json;
import io.vertx.examples.twittermsa.twitterwatcher.domain.Mention;
import org.junit.jupiter.api.Test;

public class MentionTest {

  @Test
  public void testToString(){
    Mention mention = new Mention(123456789L, "twitterUser", null);
    System.out.println(mention.toString());
  }

  @Test
  public void testToJsonObject(){
    Mention mention = new Mention(123456789L, "twitterUser", "Hello, World!");
    System.out.println(Json.encodePrettily(mention));

  }
}
