package ui.core.controls;

import com.microsoft.playwright.Dialog;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public abstract class WebDialog {
	protected String expectedAlertMessage;
	protected AtomicReference<String> alertMessage = new AtomicReference<>("");
	protected AtomicReference<String> promptResponse = new AtomicReference<>("");

	public String getAlertMessage() {
		return alertMessage.get();
	}

	public String getExpectedAlertMessage() {
		return expectedAlertMessage;
	}

	public WebDialog withExpectedAlertMessage(String expectedAlertMessage) {
		this.expectedAlertMessage = expectedAlertMessage;
		return this;
	}

	public WebDialog withPromptResponse(String promptResponse) {
		this.promptResponse = new AtomicReference<>(promptResponse);
		return this;
	}

	public abstract Consumer<Dialog> getDialogConsumer();

}
