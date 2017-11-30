package top.jnaw.jee.platform.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("card","id",Card.class);
		arp.addMapping("card_people_status", "id", CardPeopleStatus.class);
		arp.addMapping("card_type", "id", CardType.class);
		arp.addMapping("comment", "id", Comment.class);
		arp.addMapping("department", "id", Department.class);
		arp.addMapping("departments_users", "id", DepartmentsUsers.class);
		arp.addMapping("notice", "id", Notice.class);
		arp.addMapping("notice_people", "id", NoticePeople.class);
		arp.addMapping("notice_type", "id", NoticeType.class);
		arp.addMapping("opinion", "id", Opinion.class);
		arp.addMapping("roles_permissions", "id", RolesPermissions.class);
		arp.addMapping("team", "id", Team.class);
		arp.addMapping("user_roles", "id", UserRoles.class);
		arp.addMapping("user_team", "id", UserTeam.class);
		arp.addMapping("users", "id", Users.class);
	}
}
