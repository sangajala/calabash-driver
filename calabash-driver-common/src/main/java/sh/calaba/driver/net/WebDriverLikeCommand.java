/*
 * Copyright 2012 ios-driver committers. Copyright 2012 calabash-driver committers.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package sh.calaba.driver.net;

import org.json.JSONArray;
import org.json.JSONObject;

import sh.calaba.driver.exceptions.CalabashException;
import sh.calaba.driver.model.ButtonSupport;
import sh.calaba.driver.model.DeviceSupport;
import sh.calaba.driver.model.L10nSupport;
import sh.calaba.driver.model.ListItemSupport;
import sh.calaba.driver.model.NativeSearchSupport;
import sh.calaba.driver.model.SpinnerSupport;
import sh.calaba.driver.model.TextFieldSupport;
import sh.calaba.driver.model.ViewSupport;
import sh.calaba.driver.model.WaitingSupport;
import sh.calaba.driver.model.WebViewSupport;

public enum WebDriverLikeCommand {
  NEW_SESSION("POST", "/session", String.class),
  GET_SESSION("GET", "/session/:sessionId", JSONObject.class),
  GET_SESSIONS("GET", "/sessions", JSONArray.class),
  DELETE_SESSION("DELETE", "/session/:sessionId", null),
  GET_STATUS("GET", "/status", JSONObject.class),
  BUTTON("GET", "/session/:sessionId/button", ButtonSupport.class),
  TEXT_FIELD("POST", "/session/:sessionId/textField", TextFieldSupport.class),
  SPINNER("POST", "/session/:sessionId/spinner", SpinnerSupport.class),
  VIEW("GET", "/session/:sessionId/view", ViewSupport.class),
  DEVICE("POST", "/session/:sessionId/view", DeviceSupport.class),
  L10N_SUPPORT("GET", "/session/:sessionId/l10nSupport", L10nSupport.class),
  WAIT("POST", "/session/:sessionId/wait", WaitingSupport.class),
  SCREENSHOT_WITH_NAME("GET", "/session/:sessionId/screenshotWithName", ViewSupport.class),
  SEARCH("POST", "/session/:sessionId/search", NativeSearchSupport.class),
  WEB_VIEW("POST", "/session/:sessionId/webView", WebViewSupport.class),
  LIST_ITEM("GET", "/session/:sessionId/listItem", ListItemSupport.class);

  private final String method;
  private final String path;
  private final Class<?> returnType;

  private WebDriverLikeCommand(String method, String path, Class<?> returnType) {
    this.method = method;
    this.path = path;
    this.returnType = returnType;
  }

  public String path() {
    return path;
  }

  public String method() {
    return method;
  }

  public Class<?> returnType() {
    return returnType;
  }

  public static WebDriverLikeCommand getCommand(String method, String path) {
    for (WebDriverLikeCommand command : values()) {
      if (command.isGenericFormOf(method, path)) {
        return command;
      }
    }
    throw new CalabashException("cannot find command for " + method + ", " + path);
  }

  private boolean isGenericFormOf(String method, String path) {
    String genericPath = this.path;
    String genericMethod = this.method;
    if (!genericMethod.equals(method)) {
      return false;
    }
    String[] genericPieces = genericPath.split("/");
    String[] pieces = path.split("/");
    if (genericPieces.length != pieces.length) {
      return false;
    } else {
      for (int i = 0; i < pieces.length; i++) {
        String genericPiece = genericPieces[i];
        if (genericPiece.startsWith(":")) {
          continue;
        } else {
          if (!genericPiece.equals(pieces[i])) {
            return false;
          }
        }
      }
      return true;
    }
  }

  public int getIndex(String variable) {
    String[] pieces = path.split("/");

    for (int i = 0; i < pieces.length; i++) {
      String piece = pieces[i];
      if (piece.startsWith(":") && piece.equals(variable)) {
        return i;
      }
    }
    throw new CalabashException("cannot find the variable " + variable + " in " + path);
  }

}
