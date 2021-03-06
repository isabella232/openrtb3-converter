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

import com.fasterxml.jackson.databind.JavaType;
import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Imp;
import net.media.openrtb25.request.Metric;
import net.media.openrtb3.Item;
import net.media.utils.CommonConstants;
import net.media.utils.JacksonObjectMapperUtils;
import net.media.utils.Provider;

import java.util.Collection;

import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class CustomImpToItemConverter
  extends net.media.converters.request25toRequest30.ImpToItemConverter {

  private static final JavaType javaTypeForMetricCollection =
    JacksonObjectMapperUtils.getMapper()
      .getTypeFactory()
      .constructCollectionType(Collection.class, Metric.class);

  public void enhance(Imp imp, Item item, Config config, Provider converterProvider)
    throws OpenRtbConverterException {
    if (imp == null || item == null) {
      return;
    }
    if (nonNull(imp.getExt())) {
      if (imp.getExt().containsKey(CommonConstants.METRIC)) {
        try {
          imp.setMetric(null);
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting metric from imp.ext.metric", e);
        }
        imp.getExt().remove(CommonConstants.METRIC);
      }
    }
    super.enhance(imp, item, config, converterProvider);
  }
}
