/*
 * Copyright 2012 calabash-driver committers.
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
package sh.calaba.driver.client.model.impl;

import sh.calaba.driver.client.CalabashCommands;
import sh.calaba.driver.client.RemoteCalabashAndroidDriver;
import sh.calaba.driver.model.ButtonSupport;
import sh.calaba.driver.model.By;

/**
 * Default {@link ButtonSupport} implementation.
 * 
 * @author ddary
 */
public class ButtonImpl extends RemoteObject implements ButtonSupport {
  private By by;

  public ButtonImpl(RemoteCalabashAndroidDriver driver, By by) {
    super(driver);
    this.by = by;
  }

  @Override
  public void press() {
    if (by instanceof By.Index) {
      executeCalabashCommand(CalabashCommands.PRESS_BUTTON_NUMBER, by.getIdentifier());
    } else if (by instanceof By.Text) {
      executeCalabashCommand(CalabashCommands.PRESS, by.getIdentifier());
    } else if (by instanceof By.Id) {
      executeCalabashCommand(CalabashCommands.PRESS, by.getIdentifier());
    } else {
      throw new IllegalArgumentException("By not available.");
    }
  }
}
