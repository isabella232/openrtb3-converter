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

package net.media.converters.request25toRequest30;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Banner;
import net.media.openrtb25.request.Format;
import net.media.openrtb3.DisplayFormat;
import net.media.openrtb3.DisplayPlacement;
import net.media.utils.CollectionUtils;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.media.utils.CollectionUtils.copyCollection;
import static net.media.utils.ExtUtils.*;

/** Created by rajat.go on 03/01/19. */
public class BannerToDisplayPlacementConverter implements Converter<Banner, DisplayPlacement> {

  private static final List<String> extraFieldsInExt = new ArrayList<>();

  static {
    extraFieldsInExt.add(CommonConstants.UNIT);
    extraFieldsInExt.add(CommonConstants.CTYPE);
    extraFieldsInExt.add(CommonConstants.PTYPE);
    extraFieldsInExt.add(CommonConstants.CONTEXT);
    extraFieldsInExt.add(CommonConstants.PRIV);
    extraFieldsInExt.add(CommonConstants.SEQ);
  }

  @Override
  public DisplayPlacement map(Banner banner, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(banner)) {
      return null;
    }
    DisplayPlacement displayPlacement = new DisplayPlacement();
    enhance(banner, displayPlacement, config, converterProvider);
    return displayPlacement;
  }

  @Override
  public void enhance(
      Banner banner, DisplayPlacement displayPlacement, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (isNull(banner) || isNull(displayPlacement)) {
      return;
    }
    displayPlacement.setDisplayfmt(formatListToDisplayFormatList(banner.getFormat(), config));
    if (nonNull(displayPlacement.getDisplayfmt())) {
      for (DisplayFormat displayFormat : displayPlacement.getDisplayfmt()) {
        Collection<Integer> expdir = impBannerExpdir(banner);
        displayFormat.setExpdir(copyCollection(expdir, config));
      }
    }
    displayPlacement.setMime(copyCollection(banner.getMimes(), config));
    displayPlacement.setPos(banner.getPos());
    displayPlacement.setTopframe(banner.getTopframe());
    displayPlacement.setApi(copyCollection(banner.getApi(), config));
    displayPlacement.setW(banner.getW());
    displayPlacement.setH(banner.getH());
    Map<String, Object> bannerExt = banner.getExt();
    fetchFromExt(
      displayPlacement::setUnit,
      bannerExt,
      CommonConstants.UNIT,
      "error while setting unit from Banner.ext");
    fetchCollectionFromExt(
      displayPlacement::setCtype,
      bannerExt,
      CommonConstants.CTYPE,
      "error while setting ctype from Banner.ext",
      config);
    fetchFromExt(
      displayPlacement::setPtype,
      bannerExt,
      CommonConstants.PTYPE,
      "error while setting ptype from Banner.ext");
    fetchFromExt(
      displayPlacement::setContext,
      bannerExt,
      CommonConstants.CONTEXT,
      "error while setting context from Banner.ext");
    fetchFromExt(
      displayPlacement::setPriv,
      bannerExt,
      CommonConstants.PRIV,
      "error while setting priv from Banner.ext");

    if (isNull(displayPlacement.getExt())) {
      displayPlacement.setExt(new HashMap<>());
    }
    if (nonNull(bannerExt)) {
      displayPlacement.getExt().putAll(bannerExt);
    }
    putToExt(
      () -> copyCollection(banner.getBtype(), config),
      displayPlacement.getExt(),
      CommonConstants.BTYPE,
      displayPlacement::setExt);
    putToExt(
      banner::getId, displayPlacement.getExt(), CommonConstants.ID, displayPlacement::setExt);
    putToExt(
      banner::getHmax, displayPlacement.getExt(), CommonConstants.HMAX, displayPlacement::setExt);
    putToExt(
      banner::getHmin, displayPlacement.getExt(), CommonConstants.HMIN, displayPlacement::setExt);
    putToExt(
      banner::getWmax, displayPlacement.getExt(), CommonConstants.WMAX, displayPlacement::setExt);
    putToExt(
      banner::getWmin, displayPlacement.getExt(), CommonConstants.WMIN, displayPlacement::setExt);
    removeFromExt(displayPlacement.getExt(), extraFieldsInExt);
  }

  private Collection<Integer> impBannerExpdir(Banner banner) {
    Collection<Integer> expdir = banner.getExpdir();
    if (expdir == null) {
      return null;
    }
    return expdir;
  }

  private Collection<DisplayFormat> formatListToDisplayFormatList(
      Collection<Format> list, Config config) {
    if (list == null) {
      return null;
    }

    List<DisplayFormat> list1 = new ArrayList<>(list.size());
    for (Format format : list) {
      list1.add(map(format, config));
    }

    return list1;
  }

  private DisplayFormat map(Format format, Config config) {
    if (format == null) {
      return null;
    }

    DisplayFormat displayFormat = new DisplayFormat();
    if (nonNull(format.getExt())) {
      displayFormat.setExt(new HashMap<>(format.getExt()));
    }
    displayFormat.setW(format.getW());
    displayFormat.setH(format.getH());
    displayFormat.setWratio(format.getWratio());
    displayFormat.setHratio(format.getHratio());
    if (nonNull(format.getWmin())) {
      if (displayFormat.getExt() == null) {
        displayFormat.setExt(new HashMap<>());
      }
    }
    putToExt(format::getWmin, displayFormat.getExt(), CommonConstants.WMIN, displayFormat::setExt);

    return displayFormat;
  }
}
