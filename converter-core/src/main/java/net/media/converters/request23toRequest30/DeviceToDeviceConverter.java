/*
 * Copyright  2019 - present. MEDIA.NET ADVERTISING FZ-LLC
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

package net.media.converters.request23toRequest30;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.Device;
import net.media.utils.CommonConstants;
import net.media.utils.Provider;

import static java.util.Objects.nonNull;

/** Created by rajat.go on 03/04/19. */
public class DeviceToDeviceConverter
    extends net.media.converters.request25toRequest30.DeviceToDeviceConverter {

  public void enhance(
      Device source, net.media.openrtb3.Device target, Config config, Provider converterProvider)
      throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey(CommonConstants.MCCMNC)) {
        try {
          source.setMccmnc((String) source.getExt().get(CommonConstants.MCCMNC));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting mccmnc from device.ext.mccmnc", e);
        }
        source.getExt().remove(CommonConstants.MCCMNC);
      }
      if (source.getExt().containsKey(CommonConstants.GEOFETCH)) {
        try {
          source.setGeofetch((Integer) source.getExt().get(CommonConstants.GEOFETCH));
        } catch (Exception e) {
          throw new OpenRtbConverterException(
              "Error in setting geofetch from device.ext.geofetch", e);
        }
        source.getExt().remove(CommonConstants.GEOFETCH);
      }
    }
    super.enhance(source, target, config, converterProvider);
  }
}
