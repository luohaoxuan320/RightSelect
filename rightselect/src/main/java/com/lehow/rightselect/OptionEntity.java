package com.lehow.rightselect;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * desc:
 * author: luoh17
 * time: 2018/12/15 17:00
 */
 class OptionEntity {

  public static final int SINGLE_CHOICE = 1;
  /**
   * 单选必填，不能反选
   */
  public static final int SINGLE_CHOICE_MUST = 2;
  public static final int MULTI_CHOICE = 3;
  @IntDef({SINGLE_CHOICE, SINGLE_CHOICE_MUST,MULTI_CHOICE})
  @Retention(RetentionPolicy.SOURCE)
  public @interface TYPE{

  }
  private @TYPE int type=SINGLE_CHOICE;
  private ArrayList<String> optionList = new ArrayList<>();
  private ArrayList<Integer> selectedIndexs=new ArrayList<>();

  public OptionEntity() {
  }

  public <T> OptionEntity(ArrayList<T> dataSrc, String selectedValue, @TYPE int type,OptionConverter<T> optionConverter) {
    this.type = type;
    String[] split = selectedValue.split(",");
    int size = dataSrc.size();
    T option;
    for (int i = 0; i < size; i++) {
      option = dataSrc.get(i);
      optionList.add(optionConverter.getTitle(option));
      if (split.length>selectedIndexs.size())for (String value : split) {
        if (optionConverter.getValue(option).equals(value)) {
          selectedIndexs.add(i);
          break;
        }
      }
    }
  }


  public int size(){
    return optionList.size();
  }

  public String getItem(int position) {
    return optionList.get(position);
  }

  public boolean isSelected(int position) {
    return selectedIndexs.contains(position);
  }

  public ArrayList<Integer> getSelectedIndexs() {
    return selectedIndexs;
  }

  /**
   *
   * @param position
   * @return true 需要关闭
   */
  public boolean select(int position) {
    int indexOf = selectedIndexs.indexOf(position);
    if (indexOf != -1) {//重复点击已选项
      if (type == SINGLE_CHOICE_MUST) {
        return false;//不移除
      }
      selectedIndexs.remove(indexOf);//移除掉
      return false;
    }else{
      if (type == MULTI_CHOICE) {
        selectedIndexs.add(position);//添加进去
        return false;
      }
      //非多选，先清空
      selectedIndexs.clear();
      selectedIndexs.add(position);//添加进去
      return true;
    }
  }
}
