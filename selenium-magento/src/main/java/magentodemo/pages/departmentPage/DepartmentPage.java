package magentodemo.pages.departmentPage;

import magentodemo.MagentoDemoSite;
import magentodemo.pages.BaseMagentoDemoPage;

public class DepartmentPage extends BaseMagentoDemoPage<DepartmentPage> {

	public DepartmentPage(MagentoDemoSite site, String department) {
		super(site, String.format("%s.html", department));
	}
}
