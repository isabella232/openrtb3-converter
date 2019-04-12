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

package net.media.openrtb25.response.nativeresponse;

import java.util.Map;

public class AssetResponse {

  private static final Integer DEFAULT_REQUIRED = 0;

  private Integer id;

  private Integer required = DEFAULT_REQUIRED;

  private NativeTitle title;

  private NativeImage img;

  private NativeVideo video;

  private NativeData data;

  private Link link;

  private Map<String, Object> ext;

  public AssetResponse() {}

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getRequired() {
    return this.required;
  }

  public void setRequired(Integer required) {
    this.required = required;
  }

  public NativeTitle getTitle() {
    return this.title;
  }

  public void setTitle(NativeTitle title) {
    this.title = title;
  }

  public NativeImage getImg() {
    return this.img;
  }

  public void setImg(NativeImage img) {
    this.img = img;
  }

  public NativeVideo getVideo() {
    return this.video;
  }

  public void setVideo(NativeVideo video) {
    this.video = video;
  }

  public NativeData getData() {
    return this.data;
  }

  public void setData(NativeData data) {
    this.data = data;
  }

  public Link getLink() {
    return this.link;
  }

  public void setLink(Link link) {
    this.link = link;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof AssetResponse)) return false;
    final AssetResponse other = (AssetResponse) o;
    if (!other.canEqual(this)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    final Object this$required = this.getRequired();
    final Object other$required = other.getRequired();
    if (this$required == null ? other$required != null : !this$required.equals(other$required))
      return false;
    final Object this$title = this.getTitle();
    final Object other$title = other.getTitle();
    if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
    final Object this$img = this.getImg();
    final Object other$img = other.getImg();
    if (this$img == null ? other$img != null : !this$img.equals(other$img)) return false;
    final Object this$video = this.getVideo();
    final Object other$video = other.getVideo();
    if (this$video == null ? other$video != null : !this$video.equals(other$video)) return false;
    final Object this$data = this.getData();
    final Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
    final Object this$link = this.getLink();
    final Object other$link = other.getLink();
    if (this$link == null ? other$link != null : !this$link.equals(other$link)) return false;
    final Object this$ext = this.getExt();
    final Object other$ext = other.getExt();
    return this$ext == null ? other$ext == null : this$ext.equals(other$ext);
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final Object $required = this.getRequired();
    result = result * PRIME + ($required == null ? 43 : $required.hashCode());
    final Object $title = this.getTitle();
    result = result * PRIME + ($title == null ? 43 : $title.hashCode());
    final Object $img = this.getImg();
    result = result * PRIME + ($img == null ? 43 : $img.hashCode());
    final Object $video = this.getVideo();
    result = result * PRIME + ($video == null ? 43 : $video.hashCode());
    final Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    final Object $link = this.getLink();
    result = result * PRIME + ($link == null ? 43 : $link.hashCode());
    final Object $ext = this.getExt();
    result = result * PRIME + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof AssetResponse;
  }

  public String toString() {
    return "net.media.openrtb25.response.nativeresponse.AssetResponse(id="
        + this.getId()
        + ", required="
        + this.getRequired()
        + ", title="
        + this.getTitle()
        + ", img="
        + this.getImg()
        + ", video="
        + this.getVideo()
        + ", data="
        + this.getData()
        + ", link="
        + this.getLink()
        + ", ext="
        + this.getExt()
        + ")";
  }
}
