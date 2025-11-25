package com.example.stefani_antonio_s2216470;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    // link to flag website i used to find countries flags https://flagpedia.net/download/images

    private List<Currency> currencyList;
    private OnItemClickListener clickListener;

    private static final Map<String, Integer> FLAG_MAP = createFlagMap();

    private static Map<String, Integer> createFlagMap() {
        Map<String, Integer> map = new HashMap<>();

        // Map Currency Code (3-letter) to Country Code from 2-letter PNG file name
        // Ensure these match the files you pasted into res/drawable/
        map.put("GBP", R.drawable.gb);
        map.put("USD", R.drawable.us);
        map.put("JPY", R.drawable.jp);


        map.put("AED", R.drawable.ae);
        map.put("ANG", R.drawable.cw);
        map.put("ARS", R.drawable.ar);
        map.put("AUD", R.drawable.au);
        map.put("BDT", R.drawable.bd);
        map.put("BGN", R.drawable.bg);
        map.put("BHD", R.drawable.bh);
        map.put("BND", R.drawable.bn);
        map.put("BOB", R.drawable.bo);
        map.put("BRL", R.drawable.br);
        map.put("BWP", R.drawable.bw);
        map.put("CAD", R.drawable.ca);
        map.put("CHF", R.drawable.ch);
        map.put("DZD", R.drawable.dz);
        map.put("ALL", R.drawable.al);
        map.put("BSD", R.drawable.bs);
        map.put("BBD", R.drawable.bb);
        map.put("BZD", R.drawable.bz);
        map.put("BTN", R.drawable.bt);
        map.put("BIF", R.drawable.bi);
        map.put("CNY", R.drawable.cn);
        map.put("KHR", R.drawable.kh);
        map.put("CVE", R.drawable.cv);
        map.put("XOF", R.drawable.bj);


        map.put("CLP", R.drawable.cl);
        map.put("COP", R.drawable.co);
        map.put("CRC", R.drawable.cr);
        map.put("CUP", R.drawable.cu);
        map.put("CZK", R.drawable.cz);
        map.put("DKK", R.drawable.dk);
        map.put("DOP", R.drawable.dr);


        map.put("EGP", R.drawable.eg);
        map.put("ETB", R.drawable.et);
        map.put("FJD", R.drawable.fj);
        map.put("GMD", R.drawable.gm);
        map.put("GNF", R.drawable.gn);
        map.put("GTQ", R.drawable.gt);
        map.put("GYD", R.drawable.gy);


        map.put("HKD", R.drawable.hk);
        map.put("HNL", R.drawable.hn);
        map.put("HRK", R.drawable.hr);
        map.put("HTG", R.drawable.ht);
        map.put("HUF", R.drawable.hu);
        map.put("IDR", R.drawable.id);
        map.put("ILS", R.drawable.il);
        map.put("INR", R.drawable.in);
        map.put("IQD", R.drawable.iq);
        map.put("IRR", R.drawable.ir);
        map.put("ISK", R.drawable.is);


        map.put("JMD", R.drawable.jm);
        map.put("JOD", R.drawable.jo);
        map.put("KZT", R.drawable.kz);
        map.put("KES", R.drawable.ke);
        map.put("KRW", R.drawable.kr);
        map.put("KWD", R.drawable.kw);
        map.put("KPW", R.drawable.kp);
        map.put("KGS", R.drawable.kg);

        map.put("LAK", R.drawable.la);
        map.put("LBP", R.drawable.lb);
        map.put("LSL", R.drawable.ls);
        map.put("LRD", R.drawable.lr);
        map.put("LYD", R.drawable.ly);
        map.put("LKR", R.drawable.lk);

        map.put("MOP", R.drawable.mo);
        map.put("MKD", R.drawable.mk);
        map.put("MWK", R.drawable.mw);
        map.put("MYR", R.drawable.my);
        map.put("MVR", R.drawable.mv);
        map.put("MRO", R.drawable.mr);
        map.put("MUR", R.drawable.mu);
        map.put("MXN", R.drawable.mx);
        map.put("MDL", R.drawable.md);
        map.put("MNT", R.drawable.mn);
        map.put("MAD", R.drawable.ma);
        map.put("MMK", R.drawable.mm);
        map.put("MGA", R.drawable.mg);
        map.put("MZN", R.drawable.mz);

        map.put("NAD", R.drawable.na);
        map.put("NPR", R.drawable.np);
        map.put("NZD", R.drawable.nz);
        map.put("NIO", R.drawable.ni);
        map.put("NGN", R.drawable.ng);
        map.put("NOK", R.drawable.no);

        map.put("OMR", R.drawable.om);
        map.put("PKR", R.drawable.pk);
        map.put("PGK", R.drawable.pg);
        map.put("PYG", R.drawable.py);
        map.put("PEN", R.drawable.pe);
        map.put("PHP", R.drawable.ph);
        map.put("PLN", R.drawable.pl);

        map.put("QAR", R.drawable.qa);
        map.put("RON", R.drawable.ro);
        map.put("RUB", R.drawable.ru);
        map.put("RWF", R.drawable.rw);
        map.put("RSD", R.drawable.rs);

        map.put("WST", R.drawable.ws);
        map.put("SAR", R.drawable.sa);
        map.put("SCR", R.drawable.sc);
        map.put("SLL", R.drawable.sl);
        map.put("SGD", R.drawable.sg);
        map.put("SBD", R.drawable.sb);
        map.put("SOS", R.drawable.so);
        map.put("ZAR", R.drawable.za);
        map.put("SDG", R.drawable.sd);
        map.put("SZL", R.drawable.sz);
        map.put("SEK", R.drawable.se);
        map.put("SYP", R.drawable.sy);
        map.put("SRD", R.drawable.sr);

        map.put("THB", R.drawable.th);
        map.put("TRY", R.drawable.tr);
        map.put("TWD", R.drawable.tw);
        map.put("TZS", R.drawable.tz);
        map.put("TOP", R.drawable.to);
        map.put("TTD", R.drawable.tt);
        map.put("TND", R.drawable.tn);
        map.put("UGX", R.drawable.ug);
        map.put("UAH", R.drawable.ua);
        map.put("UYU", R.drawable.uy);
        map.put("UZS", R.drawable.uz);

        map.put("VUV", R.drawable.vu);
        map.put("VND", R.drawable.vn);
        map.put("YER", R.drawable.ye);
        map.put("ZMW", R.drawable.zm);

        map.put("GHS", R.drawable.gh);
        map.put("BYN", R.drawable.by);
        map.put("AFN", R.drawable.af);
        map.put("AOA", R.drawable.ao);
        map.put("AMD", R.drawable.am);
        map.put("AZN", R.drawable.az);
        map.put("BAM", R.drawable.ba);
        map.put("CDF", R.drawable.cd);
        map.put("ERN", R.drawable.er);
        map.put("GEL", R.drawable.ge);
        map.put("TJS", R.drawable.tj);
        map.put("TMT", R.drawable.tm);

        map.put("XAF", R.drawable.cm);
        map.put("XCD", R.drawable.vc);


        map.put("ZMK", R.drawable.zm);
        map.put("BYR", R.drawable.by);
        map.put("VEF", R.drawable.ve);
        map.put("ZWD", R.drawable.zw);
        map.put("STD", R.drawable.st);


        map.put("AWG", R.drawable.aw);
        map.put("BMD", R.drawable.bm);
        map.put("FKP", R.drawable.fk);
        map.put("PAB", R.drawable.pa);
        map.put("SHP", R.drawable.sh);
        map.put("KYD", R.drawable.ky);
        map.put("SVC", R.drawable.sv);


        map.put("XPF", R.drawable.fr);

        map.put("EEK", R.drawable.ee);
        map.put("LTL", R.drawable.lt);
        map.put("LVL", R.drawable.lv);
        map.put("SKK", R.drawable.sk);
        map.put("KMF", R.drawable.km);
        map.put("DJF",R.drawable.dj);

       map.put("EUR", R.drawable.eu);

       // used for getting bitcoin image https://www.freepnglogos.com/images/bitcoin-15478.html

        map.put("BTC", R.drawable.bit);

        return map;
    }

    public interface OnItemClickListener {
        void onItemClick(Currency currency);
    }

    // Constructor
    public CurrencyAdapter(List<Currency> list, OnItemClickListener listener) {
        this.currencyList = list;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout from currency_list_item.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currencylistitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Currency currency = currencyList.get(position);

        double rate = currency.getRate();


        // Binding Data
        holder.tvCurrencyCode.setText(currency.getCurrencyCode());
        holder.tvDescription.setText(currency.getDescription());
        holder.tvRate.setText(String.format("%.2f", rate));


        // make sure the correct flag is displayed
        String code = currency.getCurrencyCode();

        Integer flagResId = FLAG_MAP.get(code);

        if (flagResId != null) {
            // Set the correct flag image and make it visible
            holder.currencyFlag.setImageResource(flagResId);
            holder.currencyFlag.setVisibility(View.VISIBLE);
        } else {
            // Flag not found don't show a flag
            holder.currencyFlag.setVisibility(View.GONE);
        }


        //  Color Coding Logic

        int color;

        if (rate < 1.0) {
            color = Color.parseColor("#FFCCBC"); //  Orange (Weak vs GBP)
        } else if (rate >= 1.0 && rate < 5.0) {
            color = Color.parseColor("#C8E6C9"); //  Green (Moderate)
        } else if (rate >= 5.0 && rate < 10.0) {
            color = Color.parseColor("#BBDEFB"); //  Blue (Strong)
        } else {
            color = Color.parseColor("#EDE7F6"); // Grayish Purple (Very Strong/High Value)
        }
        holder.itemLayout.setBackgroundColor(color);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(currency);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    // Method to update the list (used for search filtering)
    public void updateList(List<Currency> newList) {
        currencyList = newList;
        notifyDataSetChanged();
    }


    // ViewHolder Class: Holds the views for each list item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCurrencyCode;
        public TextView tvDescription;
        public TextView tvRate;

        public ImageView currencyFlag;

        public LinearLayout itemLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            tvCurrencyCode = itemView.findViewById(R.id.tvCurrencyCode);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvRate=itemView.findViewById(R.id.tvRate);
            currencyFlag = itemView.findViewById(R.id.currencyFlag);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}