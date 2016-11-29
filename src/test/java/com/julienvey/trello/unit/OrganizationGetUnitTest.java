package com.julienvey.trello.unit;

import com.julienvey.trello.Trello;
import com.julienvey.trello.TrelloHttpClient;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Member;
import com.julienvey.trello.domain.Organization;
import com.julienvey.trello.impl.TrelloImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class OrganizationGetUnitTest {
    private Trello trello;
    private TrelloHttpClient httpClient;

    @Before
    public void setUp() {
        httpClient = Mockito.mock(TrelloHttpClient.class);
        trello = new TrelloImpl("", "", httpClient);
    }

    @Test
    public void testGetOrganization() {
        //Given
        Organization mockOrganization = new Organization();
        mockOrganization.setId("idOrg");

        when(httpClient.get(anyString(), any(Class.class), (String[]) anyVararg())).thenReturn(mockOrganization);

        //When
        Organization organization = trello.getOrganization("idOrg");

        //Then
        assertThat(organization).isNotNull();
        assertThat(organization.getId()).isEqualTo("idOrg");

        verify(httpClient).get(eq("https://api.trello.com/1/organizations/{organizationId}?key={applicationKey}&token={userToken}"),
                eq(Organization.class), eq("idOrg"), eq(""), eq(""));
        verifyNoMoreInteractions(httpClient);
    }

    @Test
    public void testGetOrganizationBoards() {
        //Given
        Board board1 = new Board();
        board1.setId("idBoard1");
        Board board2 = new Board();
        board2.setId("idBoard2");
        Board board3 = new Board();
        board3.setId("idBoard3");

        when(httpClient.get(anyString(), any(Class.class), (String[]) anyVararg())).thenReturn(new Board[]{board1, board2, board3});

        //When
        List<Board> organizationBoards = trello.getOrganizationBoards("idOrg");

        //Then
        assertThat(organizationBoards).isNotNull();
        assertThat(organizationBoards).hasSize(3);
        verify(httpClient).get(eq("https://api.trello.com/1/organizations/{organizationId}/boards?key={applicationKey}&token={userToken}"),
                eq(Board[].class), eq("idOrg"), eq(""), eq(""));
        verifyNoMoreInteractions(httpClient);
    }

    @Test
    public void testGetOrganizationMembers() {
        //Given
        Member member1 = new Member();
        member1.setId("idBoard1");
        Member member2 = new Member();
        member2.setId("idBoard2");
        Member member3 = new Member();
        member3.setId("idBoard3");

        when(httpClient.get(anyString(), any(Class.class), (String[]) anyVararg())).thenReturn(new Member[]{member1, member2, member3});

        //When
        List<Member> organizationMembers = trello.getOrganizationMembers("idOrg");

        //Then
        assertThat(organizationMembers).isNotNull();
        assertThat(organizationMembers).hasSize(3);
        verify(httpClient).get(eq("https://api.trello.com/1/organizations/{organizationId}/members?key={applicationKey}&token={userToken}"),
                eq(Member[].class), eq("idOrg"), eq(""), eq(""));
        verifyNoMoreInteractions(httpClient);
    }
}
