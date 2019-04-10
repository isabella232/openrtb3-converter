package net.media.converters.request23toRequest30;

import net.media.config.Config;
import net.media.exceptions.OpenRtbConverterException;
import net.media.openrtb25.request.BidRequest2_X;
import net.media.openrtb25.request.Source;
import net.media.openrtb3.Request;
import net.media.utils.Provider;

import java.util.Collection;

import static java.util.Objects.nonNull;

/**
 * Created by rajat.go on 03/04/19.
 */
public class BidRequestToRequestConverter extends net.media.converters
  .request25toRequest30.BidRequestToRequestConverter {

  public void enhance(BidRequest2_X source, Request target, Config config, Provider
    converterProvider) throws OpenRtbConverterException {
    if (source == null || target == null) {
      return;
    }
    if (nonNull(source.getExt())) {
      if (source.getExt().containsKey("bseat")) {
        try {
          source.setBseat((Collection<String>) source.getExt().get("bseat"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting bseat from bidRequest.ext.bseat",
            e);
        }
        source.getExt().remove("bseat");
      }
      if (source.getExt().containsKey("wlang")) {
        try {
          source.setWlang((Collection<String>) source.getExt().get("wlang"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting wlang from bidRequest.ext.wlang",
            e);
        }
        source.getExt().remove("wlang");
      }
      if (source.getExt().containsKey("source")) {
        try {
          source.setSource((Source) source.getExt().get("source"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting source from bidRequest.ext.source",
            e);
        }
        source.getExt().remove("source");
      }
      if (source.getExt().containsKey("bapp")) {
        try {
          source.setBapp((Collection<String>) source.getExt().get("bapp"));
        } catch (Exception e) {
          throw new OpenRtbConverterException("Error in setting bapp from bidRequest.ext.bapp",
            e);
        }
        source.getExt().remove("bapp");
      }
    }
    super.enhance(source, target, config, converterProvider);

  }
}
