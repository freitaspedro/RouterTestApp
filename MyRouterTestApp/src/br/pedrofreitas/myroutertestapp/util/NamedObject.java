package br.pedrofreitas.myroutertestapp.util;

import java.util.ArrayList;
import java.util.List;

public class NamedObject<L, T> {

	public final List list;
	public final T object;
	
	public NamedObject(ArrayList<L> list, T object) {
		this.list = list;
		this.object = object;
	}

	public List getList() {
		return list;
	}

	public T getObject() {
		return object;
	}

	
}
