package com.haksoy.exchangealarm.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by haksoy on 28.03.2017.
 */

public class ExchangeCategory {
    private static Map<String, String> exchangeCategoryList = new LinkedHashMap<>();

    public static void createCategoty() {
        exchangeCategoryList.put("Follow", "Takip Ettiklerim");
        exchangeCategoryList.put("Alarmed", "Alarmlar");
        exchangeCategoryList.put("Ascend", "En Çok Artan Hisseler");
        exchangeCategoryList.put("Descend", "En Çok Azalan Hisseler");
        exchangeCategoryList.put("ECOIG", "En Çok İşlem Görenler");
        exchangeCategoryList.put("all", "Tüm Hisseler");
        exchangeCategoryList.put("XU100", "BIST 100");
        exchangeCategoryList.put("ISIST", "İŞ BANKASI İŞTİRAKLERİ");
        exchangeCategoryList.put("KAT50", "KATILIM 50");
        exchangeCategoryList.put("KATLM", "KATILIM");
        exchangeCategoryList.put("KATMP", "KATILIM PORTFÖY");
        exchangeCategoryList.put("X030S", "BIST 30 AGIRLIK SINIRLAMALI");
        exchangeCategoryList.put("X100S", "BIST 100 AGIRLIK SINIRLAMALI");
        exchangeCategoryList.put("XBANK", "BIST BANKA");
        exchangeCategoryList.put("XBLSM", "BIST BİLİŞİM");
        exchangeCategoryList.put("XELKT", "BIST ELEKTRİK");
        exchangeCategoryList.put("XFINK", "BIST FIN. KIR., FAKTORING");
        exchangeCategoryList.put("XGIDA", "BIST GIDA, ICECEK");
        exchangeCategoryList.put("XGMYO", "BIST GAYRIMENKUL Y.O.");
        exchangeCategoryList.put("XHARZ", "BIST HALKA ARZ ENDEKSİ");
        exchangeCategoryList.put("XHOLD", "BIST HOLDING VE YATIRIM");
        exchangeCategoryList.put("XIKIU", "BIST IKINCI ULUSAL");
        exchangeCategoryList.put("XILTM", "BIST ILETISIM");
        exchangeCategoryList.put("XINSA", "BIST İNŞAAT ENDEKSİ");
        exchangeCategoryList.put("XKAGT", "BIST ORMAN, KAGIT, BASIM");
        exchangeCategoryList.put("XKMYA", "BIST KIMYA,PETROL,PLASTIK");
        exchangeCategoryList.put("XKOBI", "BIST KOBI SANAYI");
        exchangeCategoryList.put("XKURY", "BIST KURUMSAL YONETIM");
        exchangeCategoryList.put("XMADN", "BIST MADENCİLİK");
        exchangeCategoryList.put("XMANA", "BIST METAL ANA");
        exchangeCategoryList.put("XMESY", "BIST METAL EŞYA, MAKINA");
        exchangeCategoryList.put("XSADA", "BIST ADANA");
        exchangeCategoryList.put("XSANK", "BIST ANKARA");
        exchangeCategoryList.put("XSANT", "BIST ANTALYA");
        exchangeCategoryList.put("XSBAL", "BIST BALIKESİR");
        exchangeCategoryList.put("XSBUR", "BIST BURSA");
        exchangeCategoryList.put("XSDNZ", "BIST DENİZLİ");
        exchangeCategoryList.put("XSGRT", "BIST SİGORTA");
        exchangeCategoryList.put("XSIST", "BIST İSTANBUL");
        exchangeCategoryList.put("XSIZM", "BIST İZMİR");
        exchangeCategoryList.put("XSKAY", "BIST KAYSERİ");
        exchangeCategoryList.put("XSKOC", "BIST KOCAELİ");
        exchangeCategoryList.put("XSKON", "BIST KONYA");
        exchangeCategoryList.put("XSPOR", "BIST SPOR");
        exchangeCategoryList.put("XSTKR", "BIST TEKİRDAĞ");
        exchangeCategoryList.put("XTAST", "BIST TAŞ, TOPRAK");
        exchangeCategoryList.put("XTCRT", "BIST TİCARET");
        exchangeCategoryList.put("XTEKS", "BIST TEKSTİL, DERI");
        exchangeCategoryList.put("XTM25", "BIST TEMETTÜ 25");
        exchangeCategoryList.put("XTMTU", "BIST TEMETTÜ");
        exchangeCategoryList.put("XTRZM", "BIST TURİZM");
        exchangeCategoryList.put("XTUMY", "BIST ULUSAL TÜM-100");
        exchangeCategoryList.put("XU030", "BIST 30");
        exchangeCategoryList.put("XU050", "BIST 50");
        exchangeCategoryList.put("XUHIZ", "BIST ULUSAL-HİZMETLER");
        exchangeCategoryList.put("XULAS", "BIST ULAŞTIRMA");
        exchangeCategoryList.put("XULUS", "BIST ULUSAL");
        exchangeCategoryList.put("XUMAL", "BIST ULUSAL-MALİ");
        exchangeCategoryList.put("XUSIN", "BIST SINAİ");
        exchangeCategoryList.put("XUSRD", "BIST SÜRDÜREBİLİRLİK");
        exchangeCategoryList.put("XUTEK", "BIST ULUSAL-TEKNOLOJİ");
        exchangeCategoryList.put("XUTUM", "BIST ULUSAL-TUM");
        exchangeCategoryList.put("XYORT", "BIST YATIRIM ORT.");
        exchangeCategoryList.put("XYUZO", "BIST ULUSAL 100-30");
    }

    public static Map<String, String> getExchangeCategoryList() {
        return exchangeCategoryList;
    }

    public static String getValue(String key) {
        return exchangeCategoryList.get(key);
    }

    public static String getKey(String value) {
        for (String key : exchangeCategoryList.keySet()) {
            if (exchangeCategoryList.get(key).equals(value))
                return key;
        }
        return null;
    }
}
