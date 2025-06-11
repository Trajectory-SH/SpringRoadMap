package hello.remind.core.remind.member;

public class Member {
    /**
     * 기본형으로 받지 않는 이유는 기본형에는 Null값을 줄 수 없기 때문이다..
     */
    private Long id;
    private String name;
    private Grade grade;

    public Member(Long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
               "grade=" + grade +
               ", id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
