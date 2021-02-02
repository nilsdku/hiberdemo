package hiberdemo;

import hiberdemo.model.Group;
import hiberdemo.model.Todo;
import hiberdemo2.service.Activator;
import hiberdemo2.service.ITodoDao;

import java.util.*;

import javax.inject.Inject;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart
{
	public static final String	ID	= "hiberdemo.view";

	@Inject
	IWorkbench			workbench;
	private TreeViewer	viewer;

	public class TodoTreeContentProvider implements ITreeContentProvider
	{

		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement)
		{
			List<Group> groups = (List<Group>) inputElement;
			return groups.toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement)
		{
			if(parentElement instanceof Group)
				return ((Group) parentElement).getTodos().toArray();

			return new Object[0];
		}

		@Override
		public Object getParent(Object element)
		{
			if(element instanceof Todo)
				return ((Todo) element).getGroup();

			return null;
		}

		@Override
		public boolean hasChildren(Object element)
		{
			return element instanceof Group;
		}

	}

	private class StringLabelProvider extends ColumnLabelProvider
	{

		@Override
		public String getText(Object element)
		{
			if(element instanceof Todo)
			{
				return ((Todo) element).getText();
			}
			else
			if(element instanceof Group)
			{
				return ((Group) element).getName();
			}

			return super.getText(element);
		}

		@Override
		public Image getImage(Object obj)
		{
			if(obj instanceof Group)
			{
				return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			}

			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}

	}

	public View()
	{
		createInitialDataModel();
	}

	@Override
	public void createPartControl(Composite parent)
	{
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.getTree().setLinesVisible(true);

		TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
		viewer.getTree().getColumn(0).setWidth(200);

		column.setLabelProvider(new StringLabelProvider());
		viewer.setContentProvider(new TodoTreeContentProvider());

		viewer.setInput(Activator.getTodoDao().getGroups());

		viewer.expandAll();
	}

	@Override
	public void setFocus()
	{
		viewer.getControl().setFocus();
	}

	private void createInitialDataModel()
	{
		ITodoDao dao = Activator.getTodoDao();

		Group group_1 = new Group("Primary");
		Group group_2 = new Group("Secondary");

		dao.saveGroups(group_1, group_2);

		dao.saveTodos(
				createTodo(group_1, "Greet Amayak"), 
				createTodo(group_2, "Drink coffee"),
				createTodo(group_2, "Make tea")
			);
	}

	private Todo createTodo(Group a_group, String a_string)
	{
		Todo todo = new Todo();
		todo.setText(a_string);

		todo.setGroup(a_group);
		a_group.getTodos().add(todo);

		return todo;
	}
}