package com.osalam.infra.data.jpa.adapter;

import com.osalam.domain.person.Person;
import com.osalam.domain.vo.PNumber;
import com.osalam.infra.data.jpa.entity.PersonEntity;
import com.osalam.infra.data.jpa.mapper.PersonEntityDomainMapper;
import com.osalam.infra.data.jpa.repository.PersonJpaRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersonRepositoryAdapterTest {
    private final PersonEntityDomainMapper entityDomainMapper = mock(PersonEntityDomainMapper.class);
    private final PersonJpaRepository repository = mock(PersonJpaRepository.class);
    private final PersonRepositoryAdapter adapter = new PersonRepositoryAdapter(repository, entityDomainMapper);
    private final PersonEntity mockedEntity = mock(PersonEntity.class);
    private final Person input = mock(Person.class);
    private final Person returned = mock(Person.class);

    @Test
    void putPerson_should_store_person_in_db() {
        // Given
        given(entityDomainMapper.mapToEntity(input)).willReturn(mockedEntity);
        given(repository.save(any(PersonEntity.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(entityDomainMapper.mapToDomain(mockedEntity)).willReturn(returned);
        // When
        Person person = adapter.putPerson(input);
        // Then
        verify(entityDomainMapper).mapToEntity(input);
        verify(repository).save(mockedEntity);
        verify(entityDomainMapper).mapToDomain(mockedEntity);
        assertThat(person).isNotNull().isSameAs(returned);
    }

    @Test
    void fetch_person_by_id_should_get_person_in_db() {
        // Given
        long id = 12345678;
        PNumber pNumber = PNumber.of(id);
        given(repository.findByNumber(id)).willReturn(mockedEntity);
        given(entityDomainMapper.mapToDomain(mockedEntity)).willReturn(returned);
        // When
        Person person = adapter.fetchPersonById(pNumber);
        // Then
        verify(repository).findByNumber(id);
        verify(entityDomainMapper).mapToDomain(mockedEntity);
        assertThat(person).isNotNull().isSameAs(returned);
    }
}