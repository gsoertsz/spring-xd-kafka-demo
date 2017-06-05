package org.gsoertsz.common.messaging.dto;

import java.lang.Double;import java.lang.Integer;import java.lang.Long;import java.lang.String; /**
 * Created by gsoertsz on 26/5/17.
 */
public class Selection {
    private String market_name;
    private String sub_category_name;
    private Integer event_id;
    private String market_cmd_name;
    private String bet_type;
    private String product;
    private Long market_id;
    private Long market_selection_details_id;
    private Double win_price;
    private Integer category_id;
    private String event_name;
    private String selection_name;
    private Integer market_type_id;
    private Integer is_racing;
    private Long sub_category_id;
    private String selection_position;
    private Long prop_id;
    private Long bet_market_selection_id;
    private Integer is_live_bet;
    private Double place_price;

    // SIGN-POST - a bit unsure of the type of this
    private Double price_prefix;
    private String category_name;

    public Selection() {
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getMarket_cmd_name() {
        return market_cmd_name;
    }

    public void setMarket_cmd_name(String market_cmd_name) {
        this.market_cmd_name = market_cmd_name;
    }

    public String getBet_type() {
        return bet_type;
    }

    public void setBet_type(String bet_type) {
        this.bet_type = bet_type;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getMarket_id() {
        return market_id;
    }

    public void setMarket_id(Long market_id) {
        this.market_id = market_id;
    }

    public Long getMarket_selection_details_id() {
        return market_selection_details_id;
    }

    public void setMarket_selection_details_id(Long market_selection_details_id) {
        this.market_selection_details_id = market_selection_details_id;
    }

    public Double getWin_price() {
        return win_price;
    }

    public void setWin_price(Double win_price) {
        this.win_price = win_price;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getSelection_name() {
        return selection_name;
    }

    public void setSelection_name(String selection_name) {
        this.selection_name = selection_name;
    }

    public Integer getMarket_type_id() {
        return market_type_id;
    }

    public void setMarket_type_id(Integer market_type_id) {
        this.market_type_id = market_type_id;
    }

    public Integer getIs_racing() {
        return is_racing;
    }

    public void setIs_racing(Integer is_racing) {
        this.is_racing = is_racing;
    }

    public Long getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(Long sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSelection_position() {
        return selection_position;
    }

    public void setSelection_position(String selection_position) {
        this.selection_position = selection_position;
    }

    public Long getProp_id() {
        return prop_id;
    }

    public void setProp_id(Long prop_id) {
        this.prop_id = prop_id;
    }

    public Long getBet_market_selection_id() {
        return bet_market_selection_id;
    }

    public void setBet_market_selection_id(Long bet_market_selection_id) {
        this.bet_market_selection_id = bet_market_selection_id;
    }

    public Integer getIs_live_bet() {
        return is_live_bet;
    }

    public void setIs_live_bet(Integer is_live_bet) {
        this.is_live_bet = is_live_bet;
    }

    public Double getPlace_price() {
        return place_price;
    }

    public void setPlace_price(Double place_price) {
        this.place_price = place_price;
    }

    public Double getPrice_prefix() {
        return price_prefix;
    }

    public void setPrice_prefix(Double price_prefix) {
        this.price_prefix = price_prefix;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
