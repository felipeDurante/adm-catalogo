package com.felipe.admin.catalogo.infrastructure.category;

import com.felipe.admin.catalogo.MySqlGatewayTest;
import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



@MySqlGatewayTest
public class CategoryMySqlGatewayTest {

    final String expectedName = "Filmes";
    final String expectedDescription = "A categoria mais assistida";

    final boolean expectedIsAtiveTrue = true;
    final boolean expectedIsAtiveFalse = false;

    @Autowired
    private CategoryMySqlGateway categoryMySqlGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjectDepences() {

        Assertions.assertNotNull(categoryRepository);
        Assertions.assertNotNull(categoryMySqlGateway);
    }

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnCategoryCreated() {

        final Category aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsAtiveTrue);
        Assertions.assertEquals(0, categoryRepository.count());

        final Category actualCategory = categoryMySqlGateway.create(aCategory);
        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsAtiveTrue, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final CategoryEntity actualCategoryEntity = categoryRepository.findById(actualCategory.getId().getValue()).get();

        Assertions.assertEquals(expectedName, actualCategoryEntity.getName());
        Assertions.assertEquals(expectedDescription, actualCategoryEntity.getDescription());
        Assertions.assertEquals(expectedIsAtiveTrue, actualCategoryEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategoryEntity.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategoryEntity.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategoryEntity.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidCategory_whenCallsUpdate_shouldReturnCategoryUpdated() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Film", null, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryMySqlGateway.create(aCategory);

//        categoryRepository.saveAndFlush(CategoryEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        final var actualInvalidEntity = categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals("Film", actualInvalidEntity.getName());
        Assertions.assertNull(actualInvalidEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualInvalidEntity.isActive());

        final var aUpdatedCategory = aCategory.clone()
                .update(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = categoryMySqlGateway.update(aUpdatedCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());

    }

    @Test
    public void givenAPrePersistedCategoryAndValidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {
        final var aCategory = Category.newCategory("Filmes", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        categoryMySqlGateway.deleteById(aCategory.getId());

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenInvalidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {
        Assertions.assertEquals(0, categoryRepository.count());

        categoryMySqlGateway.deleteById(CategoryID.from("invalid"));

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAPrePersistedCategoryAndValidCategoryId_whenCallsFindById_shouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        final var actualCategory = categoryMySqlGateway.findById(aCategory.getId()).get();

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidCategoryIdNotStored_whenCallsFindById_shouldReturnEmpty() {
        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryMySqlGateway.findById(CategoryID.from("empty"));

        Assertions.assertTrue(actualCategory.isEmpty());
    }

    @Test
    public void teste() {

        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));

        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();

        while (l1 != null || l2 != null) {

            if (l1 != null) {
                str1.append((l1.val));
                l1 = l1.next;
            }
            if (l2 != null) {
                str2.append((l2.val));
                l2 = l2.next;
            }
        }
        str1.reverse();
        str2.reverse();
        int sum = Integer.parseInt(str1.toString()) + Integer.parseInt(str2.toString());

        String st = String.valueOf(sum);
        ListNode result = new ListNode(st.charAt(st.length() - 1) - '0');
        if (st.length() > 1)
            result.next = new ListNode();
        for (int i = st.length() - 1; i > 0; i--) {
            result.next = new ListNode(st.charAt(i) + '0');
        }
//
//
//            var result = new ListNode()
//        for(int i=0; i < )

//        var result = new ListNode()
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(sum);


    }

    @Test
    public void teste2() {

        ListNode head = new ListNode(1, new ListNode(1, new ListNode(2,new ListNode(3,new ListNode(3)))));
        ListNode node = head;

        while(node != null && node.next != null) {

//            if(node == null)
//                node = new ListNode(head.val);
            if(node.val < node.next.val) {
                node.next = node.next.next;
            } else
                node = node.next;
//            head = head.next;
        }
        System.out.println(node);
//        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


}
