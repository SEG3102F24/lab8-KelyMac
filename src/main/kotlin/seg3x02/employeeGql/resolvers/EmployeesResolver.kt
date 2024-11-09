package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.UUID

@Controller
class EmployeesResolver(
    private val employeesRepository: EmployeesRepository
) {

    @QueryMapping
    fun employees(): List<Employee> = employeesRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? = employeesRepository.findById(id).orElse(null)

    @MutationMapping
    fun updateEmployee(
        @Argument id: String,
        @Argument createEmployeeInput: CreateEmployeeInput
    ): Employee? {
        val employee = employeesRepository.findById(id).orElse(null) ?: return null
        employee.apply {
            name = createEmployeeInput.name ?: name
            dateOfBirth = createEmployeeInput.dateOfBirth ?: dateOfBirth
            city = createEmployeeInput.city ?: city
            salary = createEmployeeInput.salary ?: salary
            gender = createEmployeeInput.gender ?: gender
            email = createEmployeeInput.email ?: email
        }
        return employeesRepository.save(employee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): String {
        val employee = employeesRepository.findById(id).orElse(null)
        return if (employee != null) {
            employeesRepository.deleteById(id)
            "Employee with ID $id has been deleted."
        } else {
            "Employee with ID $id not found."
        }
    }
}
