package top.jnaw.jee.platform.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCardType<M extends BaseCardType<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setCreateId(java.lang.Integer createId) {
		set("create_id", createId);
	}

	public java.lang.Integer getCreateId() {
		return get("create_id");
	}

	public void setCreateTime(java.lang.String createTime) {
		set("create_time", createTime);
	}

	public java.lang.String getCreateTime() {
		return get("create_time");
	}

	public void setRemove(java.lang.Integer remove) {
		set("remove", remove);
	}

	public java.lang.Integer getRemove() {
		return get("remove");
	}

}
