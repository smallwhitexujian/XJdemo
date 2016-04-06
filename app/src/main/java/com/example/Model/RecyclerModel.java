package com.example.Model;

/**
 * Created by:      xujian
 * Version          1.0
 * Date:            16/4/5
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/5            xujian           1.0
 * Why & What is modified(修改原因):
 */
public class RecyclerModel {
    public String title;
    public String imgUrl;

    @Override
    public String toString() {
        return "RecyclerModel{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
