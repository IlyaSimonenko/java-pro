package ru.stepup.spring.coins.core.dtos;

public class ItemDto {
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ItemDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
