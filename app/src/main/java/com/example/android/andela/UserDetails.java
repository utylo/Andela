package com.example.android.andela;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.andela.model.User;
import com.squareup.picasso.Picasso;

public class UserDetails extends AppCompatActivity {

    ImageView avatarImageView;
    TextView userNameTextView;
    TextView userUrlTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareProfile();
            }
        });

        avatarImageView = (ImageView) findViewById(R.id.imageview_user_detail_avatar);
        userNameTextView = (TextView) findViewById(R.id.textview_user_detail_name);
        userUrlTextView = (TextView) findViewById(R.id.textview_user_detail_url);

        User user = getIntent().getParcelableExtra("user");

        Picasso.with(this).load(user.getAvatarUrl()).into(avatarImageView);
        userNameTextView.setText(getString(R.string.user_name_full, user.getLogin()));
        userUrlTextView.setText(getString(R.string.user_url_full, user.getHtmlUrl()));

        me.saket.bettermovementmethod.BetterLinkMovementMethod movementMethod = me.saket.bettermovementmethod.BetterLinkMovementMethod.linkify(Linkify.WEB_URLS, userUrlTextView);
        movementMethod.setOnLinkClickListener(new me.saket.bettermovementmethod.BetterLinkMovementMethod.OnLinkClickListener() {
            @Override
            public boolean onClick(TextView textView, String url) {
                getCustomTabIntentInstance().launchUrl(UserDetails.this, Uri.parse(url));
                return true;
            }
        });
    }

    private CustomTabsIntent getCustomTabIntentInstance() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        return builder.build();
    }

    private void shareProfile() {

        User user = getIntent().getParcelableExtra("user");


        String message = "Check out this awesome developer @" + user.getLogin()+", " + user.getUrl();

        ShareUtils.shareCustom(message, this);

    }
}





