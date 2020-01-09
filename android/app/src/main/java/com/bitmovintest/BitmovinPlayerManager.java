package com.bitmovintest;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import java.util.Map;

import com.bitmovin.player.BitmovinPlayer;
import com.bitmovin.player.BitmovinPlayerView;
import com.bitmovin.player.config.media.SourceConfiguration;
import com.bitmovin.player.api.event.listener.OnLicenseValidatedListener;
import com.bitmovin.player.api.event.data.LicenseValidatedEvent;
import com.bitmovin.player.api.event.listener.OnErrorListener;
import com.bitmovin.player.api.event.data.ErrorEvent;

public class BitmovinPlayerManager extends SimpleViewManager<BitmovinPlayerView> {
  public static final String REACT_CLASS = "BitmovinPlayer";

  private BitmovinPlayerView bitmovinPlayerView;
  private BitmovinPlayer bitmovinPlayer;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public BitmovinPlayerView createViewInstance(ThemedReactContext context) {
    bitmovinPlayerView = new BitmovinPlayerView(context);
    bitmovinPlayer = bitmovinPlayerView.getPlayer();

    SourceConfiguration sourceConfiguration = new SourceConfiguration();
    sourceConfiguration.addSourceItem("https://bitdash-a.akamaihd.net/content/sintel/sintel.mpd");
    bitmovinPlayer.load(sourceConfiguration);

    this.setListeners();

    return bitmovinPlayerView;
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder().put(
      "onLicenseValidated",
      MapBuilder.of(
        "phasedRegistrationNames",
        MapBuilder.of(
          "bubbled",
          "onLicenseValidated"
        )
      )
    )
    .put(
      "onError",
      MapBuilder.of(
        "phasedRegistrationNames",
        MapBuilder.of(
          "bubbled",
          "onError"
        )
      )
    ).build();
  }

  private void setListeners() {
    bitmovinPlayer.addEventListener(new OnLicenseValidatedListener() {
      @Override
      public void onLicenseValidated(LicenseValidatedEvent event) {
        WritableMap map = Arguments.createMap();
      }
    });

    bitmovinPlayer.addEventListener(new OnErrorListener() {
      @Override
      public void onError(ErrorEvent event) {
        WritableMap map = Arguments.createMap();
        WritableMap errorMap = Arguments.createMap();

        errorMap.putInt("code", event.getCode());
        errorMap.putString("message", event.getMessage());

        map.putMap("error", errorMap);
      }
    });
  }

}