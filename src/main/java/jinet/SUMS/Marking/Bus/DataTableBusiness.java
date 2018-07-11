/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jinet.SUMS.Marking.Bus;

/**
 *
 * @author jinet
 */
public class DataTableBusiness {
    private String category;
    private int      weight;
    private String    mark1;
    private String    mark2;
    private String    mark3;
    private String    mark4;
    private String    mark5;
    private String    mark6;
    private String    mark7;
    private String    mark8;
    private String    mark9;
    private String    mark10;
    private String comments;
    private String categorymark;

    public DataTableBusiness(String category, int weight, String mark1, String mark2, String mark3, String mark4, String mark5, String mark6, String mark7, String mark8, String mark9, String mark10, String comments, String categorymark) {
        this.category = category;
        this.weight = weight;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
        this.mark4 = mark4;
        this.mark5 = mark5;
        this.mark6 = mark6;
        this.mark7 = mark7;
        this.mark8 = mark8;
        this.mark9 = mark9;
        this.mark10 = mark10;
        this.comments = comments;
        this.categorymark = categorymark;
    }
    
    

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getMark1() {
        return mark1;
    }

    public void setMark1(String mark1) {
        this.mark1 = mark1;
    }

    public String getMark2() {
        return mark2;
    }

    public void setMark2(String mark2) {
        this.mark2 = mark2;
    }

    public String getMark3() {
        return mark3;
    }

    public void setMark3(String mark3) {
        this.mark3 = mark3;
    }

    public String getMark4() {
        return mark4;
    }

    public void setMark4(String mark4) {
        this.mark4 = mark4;
    }

    public String getMark5() {
        return mark5;
    }

    public void setMark5(String mark5) {
        this.mark5 = mark5;
    }

    public String getMark6() {
        return mark6;
    }

    public void setMark6(String mark6) {
        this.mark6 = mark6;
    }

    public String getMark7() {
        return mark7;
    }

    public void setMark7(String mark7) {
        this.mark7 = mark7;
    }

    public String getMark8() {
        return mark8;
    }

    public void setMark8(String mark8) {
        this.mark8 = mark8;
    }

    public String getMark9() {
        return mark9;
    }

    public void setMark9(String mark9) {
        this.mark9 = mark9;
    }

    public String getMark10() {
        return mark10;
    }

    public void setMark10(String mark10) {
        this.mark10 = mark10;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCategorymark() {
        return categorymark;
    }

    public void setCategorymark(String categorymark) {
        this.categorymark = categorymark;
    }

    @Override
    public String toString() {
        return "Marking{" + "category=" + category + ", weight=" + weight + ", mark1=" + mark1 + ", mark2=" + mark2 + ", mark3=" + mark3 + ", mark4=" + mark4 + ", mark5=" + mark5 + ", mark6=" + mark6 + ", mark7=" + mark7 + ", mark8=" + mark8 + ", mark9=" + mark9 + ", mark10=" + mark10 + ", comments=" + comments + ", categorymark=" + categorymark + '}';
    }
    
    
    
}
