package tech.demonlee.pattern.configuration.proxy.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Demon.Lee
 * @date 2020-08-16 17:37
 * @desc 用户，用户既是读者，也有可能是作者
 */
public class User {

    private final long id;
    private final String name; // login username
    private String nickname;
    private String password;
    private final Date createTime;
    private Date lastModifyTime;

    public User(long id, String name, String nickname, String password, Date createTime) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.createTime = createTime; // use input arg, just for testing easily
        this.lastModifyTime = new Date();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * check register duration is greater than 1 week/month/hour or not.
     *
     * @param duration (ms)
     * @return
     */
    public boolean checkRegisterDuration(long duration) {
        long registerDuration = System.currentTimeMillis() - this.getCreateTime().getTime();
        return registerDuration > duration;
    }

    public static List<User> generate() {
        return Arrays.stream(new int[]{8, 3, 18})
                .mapToObj(i -> new User(
                        10000 + i,
                        "jack-" + i,
                        "test-" + i, "123123", new Date(System.currentTimeMillis() - i * 24 * 3600 * 1000)))
                .collect(Collectors.toList());
    }

    public static User get(long id) {
        List<User> users = generate();
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", lastModifyTime=" + lastModifyTime +
                '}';
    }
}
