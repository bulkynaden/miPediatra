# miPediatra

## Objetivo

Creación de una aplicación donde se puedan volcar los datos de las visitas de los infantes para poder mantener no solamente el historial, sino también un control que nos permita extraer datos estadísticos propios, para así detectar posibles patrones de cuando cada niño es más propenso a enferma y estudiar los datos de las visitas rutinarias pudiendo observar alguna alteración que pueda ser necesario reportar a un especialista. 

## Antecedentes

En la vida real, muchos de nosotros tenemos hijos los cuales en más ocasiones de las deseadas tienen que visitar al médico, dichas visitas pueden realizarse por rutina (ya sea por vacunas o por revisiones), otras se realizan por cuestiones de urgencia, debido a que enferman. 
La frecuencia de estas visitas suele ser alta, por lo que es difícil, en ocasiones, recordar y controlar cuando se ha ido y por qué. La situación es aún más difícil de gestionar cuando se tiene más de un hijo.

## Requisitos

**1. Herencia:** `VisitaMedica` tiene los subtipos `Rutina` y `Urgencia`.

**2. Relación One-To-Many:** Relación entre el `Paciente` y las `VisitaMedica`.

**3. Método personalizado:** Posible método para calcular las vacunas que han sido saltadas, o la próxima vacuna a poner.

**4. Listado:** Se podrá extraer el listado de las diferentes visitas realizadas.

**5. CRUD:**

**6. URL,s del proyecto:**

[Enlace al repositorio](https://git.institutomilitar.com/WoLfMaN86/mipediatra)

**7. Despliegue:**

## Diagrama de Clases:

![diagrama](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/Imagenes/Diagramav3.png)

## Interfaz de Usuario:


### Pantalla de inicio

![Pantalla de Inicio](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/Inicio.png)

### Menús

#### Menu Mis pacientes
![Menú 1](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/Menu1.png)

#### Menu Mediciones
![Menú 2](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/Menu2.png)

#### Menu Consultas
![Menú 3](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/Menu3.png)

### Pantalla del calendario / eventos

![Pantalla de Inicio](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/CalendarioPage.png)

### Pantalla del listado de pacientes
En esta pantalla se podrá ver el listado de pacientes, añadir uno nuevo, editar y ver los detalles de cada uno.
![Pantalla de listado](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/ListadoPacientes.png)

### Pantalla de creacción de paciente
En esta pantalla se podrá añadir un nuevo paciente. Permitirá añadir todos los datos necesarios del paciente, datos de nacimiento, vacunas ya puestas, etc.

![Pantalla de CreaccionPaciente](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/AddPacientePage.png)

### Pantalla de detalles de paciente
En esta pantalla se podrá visualizar toda la información requerida de cada paciente, gráficos, próximos eventos, añadir vacunas, añadir próximas consultas médicas, etc
![Pantalla de Detalles](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/DetallesPage.png)

### Pantalla de gráficos de estatura, peso, etc.

En esta pantalla se podrán visualizar los gráficos de cada paciente, ya sea de todos simultáneamente o seleccionando uno de los pacientes.
![Pantalla de Gráficos](https://git.institutomilitar.com/WoLfMaN86/mipediatra/-/wikis/uploads/interfaz/Graficos.png)
