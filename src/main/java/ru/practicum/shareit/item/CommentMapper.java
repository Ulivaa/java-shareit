package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;

public class CommentMapper {
    public static Comment toComment(CommentDto commentDto) {
        return Comment.builder().text(commentDto.getText()).build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .item(new CommentDto.Item(comment.getItem().getId(), comment.getItem().getName()))
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }
}
