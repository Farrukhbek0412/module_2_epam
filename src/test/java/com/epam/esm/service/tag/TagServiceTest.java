package com.epam.esm.service.tag;

import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dto.BaseResponseDTO;
import com.epam.esm.domain.tag.Tag;
import com.epam.esm.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDao;

    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag(UUID.randomUUID(), "test-tag");
    }

    @Test
    public void testCreateTag() {
        given(tagDao.create(tag)).willReturn(tag);

        BaseResponseDTO<Tag> tagBaseResponseDto = tagService.create(tag);

        assertEquals(201, tagBaseResponseDto.getStatus());
        assertEquals("tag created successfully", tagBaseResponseDto.getMessage());
        Mockito.verify(tagDao, Mockito.times(1)).create(tag);
    }
    @Test
    public void testGetTagById() {
        given(tagDao.get(tag.getId())).willReturn(tag);

        BaseResponseDTO<Tag> tag1 = tagService.get(tag.getId());

        assertEquals(200, tag1.getStatus());
        assertEquals("success", tag1.getMessage());
        assertEquals("test-tag", tag1.getData().getName());

    }

    @Test
    public void testGetTagByIdThrowsException() {
        given(tagDao.get(tag.getId())).willReturn(null);

        assertThrows(DataNotFoundException.class, () -> tagService.get(tag.getId()));
        verify(tagDao, times(1)).get(tag.getId());
    }

    @Test
    public void testGetAllTags() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag(UUID.randomUUID(), "tag1");
        Tag tag2 = new Tag(UUID.randomUUID(), "tag2");
        Tag tag3 = new Tag(UUID.randomUUID(), "tag3");

        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        given(tagDao.getAll()).willReturn(tags);

        BaseResponseDTO<List<Tag>> all = tagService.getAll();

        assertEquals(3, all.getData().size());
        assertEquals(200, all.getStatus());
        assertEquals("success", all.getMessage());
        Mockito.verify(tagDao, Mockito.times(1)).getAll();
    }

    @Test
    public void testDeleteTag() {
        given(tagDao.delete(tag.getId())).willReturn(1);

        BaseResponseDTO<Tag> deleteTag = tagService.delete(tag.getId());
        assertEquals(200, deleteTag.getStatus());
        assertEquals("tag deleted successfully", deleteTag.getMessage());
        Mockito.verify(tagDao, Mockito.times(1)).delete(tag.getId());
    }
    @Test
    public void testDeleteTagThrowsException() {
        given(tagDao.delete(tag.getId())).willReturn(0);

        assertThrows(DataNotFoundException.class, () -> tagService.delete(tag.getId()));
        verify(tagDao, times(1)).delete(tag.getId());
    }
}