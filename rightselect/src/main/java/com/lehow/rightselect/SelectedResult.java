package com.lehow.rightselect;

/**
 * desc:
 * author: luoh17
 * time: 2018/12/18 13:58
 */
public class SelectedResult {
  private String values;
  private String summary;

  public SelectedResult(String values, String summary) {
    this.values = values;
    this.summary = summary;
  }

  public String getValues() {
    return values;
  }

  public String getSummary() {
    return summary;
  }
}
