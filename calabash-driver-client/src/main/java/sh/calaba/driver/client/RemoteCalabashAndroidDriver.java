package sh.calaba.driver.client;

import java.io.File;
import java.util.Map;

import sh.calaba.driver.CalabashCapabilities;
import sh.calaba.driver.client.model.impl.ButtonImpl;
import sh.calaba.driver.client.model.impl.ListItemImpl;
import sh.calaba.driver.client.model.impl.TextFieldImpl;
import sh.calaba.driver.client.model.impl.ViewImpl;
import sh.calaba.driver.client.model.impl.WaitingSupportImpl;
import sh.calaba.driver.model.ButtonSupport;
import sh.calaba.driver.model.By;
import sh.calaba.driver.model.CalabashAndroidDriver;
import sh.calaba.driver.model.ListItemSupport;
import sh.calaba.driver.model.TextFieldSupport;
import sh.calaba.driver.model.WaitingSupport;

public class RemoteCalabashAndroidDriver extends CalabashAndroidDriver {

	public RemoteCalabashAndroidDriver(String remoteURL,
			Map<String, Object> capabilities) {
		super(remoteURL, capabilities);
	}

	public RemoteCalabashAndroidDriver(String host, Integer port,
			CalabashCapabilities capa) {
		super("http://" + host + ":" + port + "/wd/hub", capa
				.getRawCapabilities());
	}

	public TextFieldSupport textField(By by) {
		return new TextFieldImpl(this, by);
	}

	public ButtonSupport button(By by) {
		return new ButtonImpl(this, by);
	}

	public ListItemSupport listItem(By by) {
		return new ListItemImpl(this, by);
	}

	public WaitingSupport waitFor() {
		return new WaitingSupportImpl(this);
	}

	public File takeScreenshot(String path) {
		return new ViewImpl(this, "").takeScreenshot(path);
	}
}