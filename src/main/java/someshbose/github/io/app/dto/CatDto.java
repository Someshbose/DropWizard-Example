package someshbose.github.io.app.dto;

import java.io.Serializable;

public class CatDto implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String id;
  private String content;

  public CatDto() {

  }

  public CatDto(String id, String content) {
    this.id = id;
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public String getId() {
    return id;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cat {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("}");
    return sb.toString();
  }
  
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
