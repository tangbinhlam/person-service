package com.osalam.rest.controller;

import com.osalam.domain.person.Person;
import com.osalam.domain.person.PersonApplicationService;
import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.share.AddressNumber;
import com.osalam.domain.vo.CreatePersonCommand;
import com.osalam.domain.vo.PNumber;
import com.osalam.rest.PersonRestConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PersonRestConfiguration.class)
@WebMvcTest
@AutoConfigureMockMvc
class PersonRestControllerIT {
    private final String baseUrl = "/rest/api/v1/persons";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonApplicationService service;

    @Test
    void create_should_return_a_complete_person() throws Exception {
        String pid = "P12345678";
        String firstName = "first Name";
        String lastName = "last name";
        String addressLine = "2000 North Trail";
        PersonalAddress mainAddress = PersonalAddress.of(AddressNumber.of(123456780L), addressLine);
        given(service.createPerson(any(CreatePersonCommand.class))).willAnswer(invocation -> {
            CreatePersonCommand command = invocation.getArgument(0);
            return Person
                    .of(command.getFirstName(), command.getLastName(), command.getBirthDate(), command.getMainAddress())
                    .withId(PNumber.of(pid))
                    .build();
        });
        mockMvc.perform(post(baseUrl)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(String.format("{" +
                        "  \"firstName\": \"%s\"," +
                        "  \"lastName\": \"%s\"," +
                        "  \"birthDate\": \"12.03.1945\"," +
                        "  \"mainAddress\": {" +
                        "    \"id\": \"A123456780\"," +
                        "    \"address\": \"%s\"" +
                        "  }" +
                        "}", firstName, lastName, addressLine)
                )
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(pid))
        ;
        ArgumentCaptor<CreatePersonCommand> captor = ArgumentCaptor.forClass(CreatePersonCommand.class);
        verify(service).createPerson(captor.capture());
        CreatePersonCommand captured = captor.getValue();
        assertThat(captured.getLastName()).isEqualTo(lastName);
        assertThat(captured.getFirstName()).isEqualTo(firstName);
        assertThat(captured.getMainAddress()).isEqualToComparingFieldByField(mainAddress);
    }

    @Test
    void readAPerson_should_answer_OK() throws Exception {
        long id = 56743245L;
        PNumber pNumber = PNumber.of(id);
        String firstName = "first Name";
        String lastName = "last name";

        AddressNumber addressNumber = AddressNumber.of(7568463725L);
        String addressLine = "2000 North Trail";
        PersonalAddress mainAddress = PersonalAddress.of(addressNumber, addressLine);
        Person returnedDto = Person.of(firstName, lastName, LocalDate.now(), mainAddress).withId(pNumber).build();
        given(service.readPerson(pNumber)).willReturn(returnedDto);
        mockMvc.perform(get(baseUrl + "/" + pNumber.format())
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.id").value(pNumber.format()))
                .andExpect(jsonPath("$.mainAddress.id").value(addressNumber.format()))
                .andExpect(jsonPath("$.mainAddress.address").value(addressLine))
        ;
    }
}