package org.insbaixcamp.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class NewsActivity extends AppCompatActivity {
    WebView wvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        wvNews=findViewById(R.id.wvNews);
        wvNews.loadUrl("http://dcshoes.es/skate/news/");

    }
}
