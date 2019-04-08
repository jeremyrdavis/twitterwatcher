package io.vertx.examples.twittermsa.twitterwatcher.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Mention {

  private Long id;

  private String screenName;

  private String text;

  public Mention() {
  }

  public Mention(Long id, String screenName, String text) {
    this.id = id;
    this.screenName = screenName;
    this.text = text;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id: " + id)
      .append("screenName: " + screenName)
      .append("text: " + text).toString();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
