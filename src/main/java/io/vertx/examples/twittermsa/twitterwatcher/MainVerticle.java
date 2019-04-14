package io.vertx.examples.twittermsa.twitterwatcher;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.examples.twittermsa.twitterwatcher.domain.Mention;
import io.vertx.ext.web.client.WebClient;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

  private static long lastMentionId;

  private HashMap<Long, Mention> mentions;

  Twitter twitter;

  WebClient webClient;

  @Override
  public void start() {

    mentions = new HashMap<>();
    twitter = TwitterFactory.getSingleton();
    webClient = WebClient.create(vertx);

    /*
      Poll Twitter for changes to the timeline
     */
    vertx.setPeriodic(5000, id -> {
      System.out.println("polling Twitter!");
      this.checkMentions();
    });

    vertx.createHttpServer()
      .requestHandler(req -> req.response().end("Tweets" + mentions.size()))
      .listen(8080);
  }

  private void checkMentions() {

    ArrayList<Mention> newMentions = new ArrayList<>();

    vertx.executeBlocking((Future<Object> future) -> {

      try {
        List<Status> statuses = twitter.getMentionsTimeline();

        System.out.println("Showing @jbossdemo's mentions.");

        for (Status s : statuses) {
          Mention mention = new Mention();
          mention.setId(s.getId());
          mention.setScreenName(s.getUser().getScreenName());
          mention.setText(s.getText());
          mentions.put(s.getId(), mention);
        }
        future.complete(mentions);
      } catch (Exception e) {
        future.fail(e.getMessage());
      }
    }, res -> {
      if (res.succeeded()) {
        filterTweets(newMentions);
        mentions.forEach((k, v) -> System.out.println("k: " + k + " v: " + v));
      } else {
        System.out.println(res.cause());
      }
    });
  }

  private void filterTweets(ArrayList<Mention> mentions) {

    mentions.forEach(m -> {
      webClient.postAbs("localhost:8081/api/filter").sendJsonObject(new JsonObject(Json.encodeToBuffer(m)), res ->{
        if (res.succeeded()) {
          System.out.println(res.result());
        }else{
        }
      });
    });
  }
}

