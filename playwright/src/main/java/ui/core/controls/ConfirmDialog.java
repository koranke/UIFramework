package ui.core.controls;

import com.microsoft.playwright.Dialog;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

@Data
@Accessors(fluent = true)
public class ConfirmDialog extends WebDialog {

	@Override
	public Consumer<Dialog> getDialogConsumer() {
		return dialog -> {
			alertMessage.set(dialog.message());
			dialog.accept();
		};
	}

}
