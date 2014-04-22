package edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel;

import java.util.List;

import javax.swing.AbstractListModel;

public abstract class AbstractStorageModel<T extends ObservableModel> extends AbstractListModel<T> implements IModelObserver{
	
	private final List<T> list;
	protected boolean serverUpdating = false;
	
	
	protected AbstractStorageModel(List<T> list){
		this.list = list;
	}
	
	
	public synchronized void add(T object){
		while(serverUpdating){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		list.add(object);
		object.addObserver(this);
		this.fireIntervalAdded(this, 0, 0);
	}
	
	
	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public T getElementAt(int index) {
		return list.get(index);
	}

}
