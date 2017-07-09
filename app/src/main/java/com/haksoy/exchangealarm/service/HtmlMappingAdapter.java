package com.haksoy.exchangealarm.service;

import com.haksoy.exchangealarm.model.Exchange;
import com.haksoy.exchangealarm.model.Market;
import com.haksoy.exchangealarm.model.MarketScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static com.haksoy.exchangealarm.model.Constants.ExchangeType;
import static com.haksoy.exchangealarm.model.Constants.MarketScreenType;

/**
 * Created by haksoy on 28.03.2017.
 */

public class HtmlMappingAdapter extends Converter.Factory {
    private static String query;

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {

        if (type == Market.class)
            return new Converter<ResponseBody, Market>() {
                @Override
                public Market convert(ResponseBody value) throws IOException {
                    Document document = null;
                    document = Jsoup.parse(value.string());
                    Elements elements = document.select("div[id=turkiye");

                    Market market = new Market();
                    Element divElement = elements.first();

                    Elements liElements = divElement.getElementsByTag("li");

                    setMarketBIST100Data(liElements.get(0), market);
                    setMarketUSDData(liElements.get(1), market);
                    setMarketEUROData(liElements.get(2), market);
                    setMarketGOLDData(liElements.get(3), market);
                    setMarketBRENTData(liElements.get(5), market);
                    return market;
                }

            };
        else if (type.toString().equals(ExchangeType))
            return new Converter<ResponseBody, List<Exchange>>() {
                @Override
                public List<Exchange> convert(ResponseBody value) throws IOException {
                    List<Exchange> responseList = new ArrayList<>();
                    Document document = null;
                    document = Jsoup.parse(value.string());
                    Elements elements = document.getElementsByAttributeValueContaining("class", query);
                    for (Element element : elements) {
                        Exchange exchange = new Exchange();
                        exchange.setName(element.attr("data-id"));
                        exchange.setPercentageChange(element.attr("data-change"));
                        Elements elementsAttributes = element.getElementsByTag("small");
                        exchange.setPrice(Float.parseFloat(elementsAttributes.get(0).text().replace(".", "").replace(",", ".")));
                        exchange.setPurchasePrice(Float.parseFloat(elementsAttributes.get(1).text().replace(".", "").replace(",", ".")));
                        exchange.setSalePrice(Float.parseFloat(elementsAttributes.get(2).text().replace(".", "").replace(",", ".")));

                        if (exchange.getPrice() <= 0)
                            continue;
                        responseList.add(exchange);
                    }

                    return responseList;
                }
            };
        else if (type.toString().equals(MarketScreenType)) {
            return new Converter<ResponseBody, List<MarketScreen>>() {
                @Override
                public List<MarketScreen> convert(ResponseBody value) throws IOException {
                    List<MarketScreen> responseList = new ArrayList<>();
                    Document document = null;
                    document = Jsoup.parse(value.string());
                    Elements elements = document.getElementsByAttributeValueContaining("class", "dbox");
                    Element element = null;
                    switch (query) {
                        case "Ascend":
                            element = elements.get(0);
                            break;
                        case "Descend":
                            element = elements.get(1);
                            break;
                        case "ECOIG":
                            element = elements.get(2);
                            break;
                    }

                    Elements lis = element.getElementsByTag("li");
                    for (Element li : lis) {
                        Elements inLi = li.getAllElements();
                        MarketScreen marketScreen = new MarketScreen();
                        marketScreen.setName(inLi.get(2).text());
                        marketScreen.setPrice(Float.parseFloat(inLi.get(4).text().replace(".", "").replace(",", ".")));
                        marketScreen.setYesterDayPrice(Float.parseFloat(inLi.get(5).text().replace(".", "").replace(",", ".")));
                        marketScreen.setPercentageChange(inLi.get(6).text().replace(".", "").replace(",", "."));
                        responseList.add(marketScreen);
                    }

                    return responseList;
                }
            };
        }

        return null;
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringConverter<>();
    }

    static class StringConverter<T> implements Converter<T, String> {
        @Override
        public String convert(T value) {
            query = (String) value;
            return null;
        }
    }

    private void setMarketBIST100Data(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String bist100Value = spanElement.text();
        market.setBist100Value(Float.parseFloat(bist100Value.replace(",", ".")));

        Element bElement = liElement.getElementsByTag("b").first();
        String bisPercentage = bElement.text();
        market.setBist100Percentage(Float.parseFloat(bisPercentage.split("%")[1].replace(",", ".")));

    }

    private void setMarketUSDData(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String usdValue = spanElement.text();
        market.setUsdValue(Float.parseFloat(usdValue.replace(",", ".")));
        Element bElement = liElement.getElementsByTag("b").first();
        String usdPercentage = bElement.text();
        market.setUsdPercentage(Float.parseFloat(usdPercentage.split("%")[1].replace(",", ".")));

    }

    private void setMarketEUROData(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String euroValue = spanElement.text();
        market.setEuroValue(Float.parseFloat(euroValue.replace(",", ".")));

        Element bElement = liElement.getElementsByTag("b").first();
        String euroPercentage = bElement.text();
        market.setEuroPercentage(Float.parseFloat(euroPercentage.split("%")[1].replace(",", ".")));

    }

    private void setMarketGOLDData(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String goldValue = spanElement.text();
        market.setGoldValue(Float.parseFloat(goldValue.replace(",", ".")));

        Element bElement = liElement.getElementsByTag("b").first();
        String goldPercentage = bElement.text();
        market.setGoldPercentage(Float.parseFloat(goldPercentage.split("%")[1].replace(",", ".")));

    }

    private void setMarketBRENTData(Element liElement, Market market) {
        Element spanElement = liElement.getElementsByTag("span").first();

        String brentValue = spanElement.text();
        market.setBrentValue(Float.parseFloat(brentValue.replace(",", ".")));

        Element bElement = liElement.getElementsByTag("b").first();
        String brentPercentage = bElement.text();
        market.setBrentPercentage(Float.parseFloat(brentPercentage.split("%")[1].replace(",", ".")));

    }
}
