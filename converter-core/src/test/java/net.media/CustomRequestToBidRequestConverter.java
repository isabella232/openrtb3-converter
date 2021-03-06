/*
 * Copyright  2019 - present. IAB Tech Lab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.media;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.driver.Conversion;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.User;
import net.media.openrtb3.*;
import net.media.utils.CollectionUtils;
import net.media.utils.MapUtils;
import net.media.utils.Provider;
import net.media.utils.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static java.util.Objects.nonNull;

public class CustomRequestToBidRequestConverter implements Converter<Request, BidRequest2_X> {

  @Override
  public BidRequest2_X map(Request source, Config config, Provider converterProvider)
    throws OpenRtbConverterException {
    if (source == null) {
      return null;
    }

    BidRequest2_X bidRequest = new BidRequest2_X();

    enhance(source, bidRequest, config, converterProvider);

    return bidRequest;
  }

  @Override
  public void enhance(
    Request source, BidRequest2_X target, Config config, Provider converterProvider)
    throws OpenRtbConverterException {
    if (source == null || target == null) return;

    Converter<net.media.openrtb3.User, User> userUserConverter =
      converterProvider.fetch(new Conversion<>(net.media.openrtb3.User.class, User.class));
    Converter<Request, User> requestUserConverter =
      converterProvider.fetch(new Conversion<>(Request.class, User.class));
    Converter<App, net.media.openrtb25.request.App> appAppConverter =
      converterProvider.fetch(new Conversion<>(App.class, net.media.openrtb25.request.App.class));
    Converter<Regs, net.media.openrtb25.request.Regs> regsRegsConverter =
      converterProvider.fetch(
        new Conversion<>(Regs.class, net.media.openrtb25.request.Regs.class));
    Converter<Site, net.media.openrtb25.request.Site> siteSiteConverter =
      converterProvider.fetch(
        new Conversion<>(Site.class, net.media.openrtb25.request.Site.class));
    Converter<Device, net.media.openrtb25.request.Device> deviceDeviceConverter =
      converterProvider.fetch(
        new Conversion<>(Device.class, net.media.openrtb25.request.Device.class));
    Converter<Source, net.media.openrtb25.request.Source> sourceSourceConverter =
      converterProvider.fetch(
        new Conversion<>(Source.class, net.media.openrtb25.request.Source.class));
    Converter<Item, net.media.openrtb25.request.Imp> itemImpConverter =
      converterProvider.fetch(new Conversion<>(Item.class, Imp.class));

    Map<String, Object> map = source.getExt();
    if (map != null) {
      target.setExt(MapUtils.copyMap(map, config));
    }

    if (source.getContext() != null) {

      if (source.getContext().getUser() != null) {
        if (target.getUser() == null) {
          target.setUser(
            userUserConverter.map(source.getContext().getUser(), config, converterProvider));
        }
      } else {
        target.setUser(null);
      }
      if (source.getCdata() != null) {
        if (target.getUser() == null) {
          target.setUser(new User());
        }
        requestUserConverter.enhance(source, target.getUser(), config, converterProvider);
      }

      App app = source.getContext().getApp();
      if (app != null) {
        target.setApp(appAppConverter.map(app, config, converterProvider));
      }

      Regs regs = source.getContext().getRegs();
      if (regs != null) {
        target.setRegs(regsRegsConverter.map(regs, config, converterProvider));
      }

      Site site = source.getContext().getSite();
      if (site != null) {
        target.setSite(siteSiteConverter.map(site, config, converterProvider));
      }
      if (target.getExt() == null) target.setExt(new HashMap<>());
      target.getExt().put(CommonConstants.CATTAX, source.getContext().getRestrictions().getCattax());
      if (source.getContext().getRestrictions() != null) {
        target.setBapp(
          CollectionUtils.copyCollection(
            source.getContext().getRestrictions().getBapp(), config));
        target.setBcat(
          CollectionUtils.copyCollection(
            source.getContext().getRestrictions().getBcat(), config));
        target.setBadv(
          CollectionUtils.copyCollection(
            source.getContext().getRestrictions().getBadv(), config));

        if (source.getContext().getRestrictions().getExt() != null) {
          if (target.getExt() == null) target.setExt(new HashMap<>());
          Restrictions restrictions = new Restrictions();
          restrictions.setCattax(null);
          restrictions.setExt(source.getContext().getRestrictions().getExt());
          target.getExt().put(CommonConstants.RESTRICTIONS, restrictions);
        }
      }

      Device device = source.getContext().getDevice();
      if (device != null) {
        target.setDevice(deviceDeviceConverter.map(device, config, converterProvider));
      }
    }
    target.setAllimps(source.getPack());
    target.setImp(
      CollectionUtils.convert(
        source.getItem(), itemImpConverter, config, converterProvider));
    if (!CollectionUtils.isEmpty(target.getImp())) {
      if (nonNull(source.getContext()) && nonNull(source.getContext().getRestrictions())) {
        for (Imp imp : target.getImp()) {
          if (nonNull(imp.getBanner())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getBanner()
                .setBattr(
                  CollectionUtils.copyCollection(
                    source.getContext().getRestrictions().getBattr(), config));
            }
          }
          if (nonNull(imp.getVideo())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getVideo()
                .setBattr(
                  CollectionUtils.copyCollection(
                    source.getContext().getRestrictions().getBattr(), config));
            }
          }
          if (nonNull(imp.getAudio())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getAudio()
                .setBattr(
                  CollectionUtils.copyCollection(
                    source.getContext().getRestrictions().getBattr(), config));
            }
          }
          if (nonNull(imp.getNat())) {
            if (nonNull(source.getContext().getRestrictions().getBattr())) {
              imp.getNat()
                .setBattr(
                  CollectionUtils.copyCollection(
                    source.getContext().getRestrictions().getBattr(), config));
            }
          }
        }
      }
    }
    target.setId(source.getId());
    target.setAt(source.getAt());
    target.setTest(source.getTest());
    target.setTmax(source.getTmax());
    target.setSource(sourceSourceConverter.map(source.getSource(), config, converterProvider));
    Collection<String> list1 = source.getCur();
    if (list1 != null) {
      target.setCur(CollectionUtils.copyCollection(list1, config));
    }

    if (source.getWseat() != null) {

      if (source.getWseat() == 0) {
        target.setBseat(CollectionUtils.copyCollection(source.getSeat(), config));
        target.setBseat(CollectionUtils.copyCollection(source.getSeat(), config));
      } else {
        target.setWseat(CollectionUtils.copyCollection(source.getSeat(), config));
      }
    }

    if (source.getItem() != null && source.getItem().size() > 0) {
      Collection<String> wlang = new HashSet<>();
      for (Item item : source.getItem()) {
        if (item.getSpec() != null
          && item.getSpec().getPlacement() != null
          && item.getSpec().getPlacement().getWlang() != null) {
          wlang.addAll(item.getSpec().getPlacement().getWlang());
        }
      }
      target.setWlang(CollectionUtils.copyCollection(wlang, config));
    }

    if (target.getImp() != null) {
      for (Imp imp : target.getImp()) {
        if (imp.getBanner() != null)
          imp.getBanner().setBattr(source.getContext().getRestrictions().getBattr());
        if (imp.getVideo() != null)
          imp.getVideo().setBattr(source.getContext().getRestrictions().getBattr());
        if (imp.getNat() != null)
          imp.getNat().setBattr(source.getContext().getRestrictions().getBattr());
        if (imp.getAudio() != null)
          imp.getAudio().setBattr(source.getContext().getRestrictions().getBattr());
      }
    }
    if (target.getExt() == null) target.setExt(new HashMap<>());
    target.getExt().put("dummy", "userIDhere");

    if (source.getContext().getDooh() == null) return;

    if (target.getExt() == null) target.setExt(new HashMap<>());
    target.getExt().put("dooh", source.getContext().getDooh());
  }
}
