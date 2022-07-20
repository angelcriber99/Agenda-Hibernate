package Agenda;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int opcion = 0;
		boolean deshabilitar = false;

		int contadorAlta = 0;
		int contadorModificar = 0;
		int contadorBuscar = 0;
		int contadorEliminar = 0;
		int contadorVerContactos = 0;

		do {
			if (!deshabilitar)

			System.out.println("\nBienvenid@ a la agenda.");
			System.out.println("\n¿Qué desea hacer?.");
			System.out.println("1. AGREGAR CONTACTO.");
			System.out.println("2. MODIFICAR CONTACTO.");
			System.out.println("3. BUSCAR CONTACTO.");
			System.out.println("4. ELIMINAR CONTACTO.");
			System.out.println("5. VER MOVIMIENTOS REALIZADOS.");
			System.out.println("6. VER TODOS LOS CONTACTOS.");
			System.out.println("0. SALIR DE LA AGENDA.");

			opcion = sc.nextInt();

			if (opcion == 1) {

				System.out.println("\nA continuaciï¿½n, inserte los siguientes datos:");
				System.out.print("Introduzca un nombre: ");
				String nombreAlta = sc.next();
				System.out.print("Introduzca un apellido: ");
				String apellidoAlta = sc.next();
				System.out.print("Introduzca un email: ");
				String emailAlta = sc.next();
				System.out.print("Introduzca un nï¿½mero de telï¿½fono: ");
				int telefonoAlta = sc.nextInt();
				EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsersDB");
				EntityManager entityManager = factory.createEntityManager();

				entityManager.getTransaction().begin();
				users newUser = new users();

				newUser.setNombre(nombreAlta);
				newUser.setApellido(apellidoAlta);
				newUser.setEmail(emailAlta);
				newUser.setTelefono(telefonoAlta);

				entityManager.persist(newUser);
				entityManager.getTransaction().commit();
				entityManager.close();
				factory.close();

				contadorAlta++;
				deshabilitar = false;

			} else if (opcion == 2) {

				System.out.println("\nPrimero, introduzca los siguientes datos:");
				System.out.print("Introduzca su Id: ");
				int IdModificar = sc.nextInt();
				System.out.print("Introduzca el nombre: ");
				String nombreModificar = sc.next();
				System.out.print("Introduzca el apellido: ");
				String apellidoModificar = sc.next();
				System.out.print("Introduzca el email: ");
				String emailModificar = sc.next();
				System.out.print("Introduzca el telefono: ");
				int telefonoModificar = sc.nextInt();

				System.out.println("\nï¿½Quï¿½ desea modificar?");
				System.out.println("1. EMAIL.");
				System.out.println("2. Nï¿½MERO DE TELï¿½FONO.");

				int opcionModificar = sc.nextInt();

				if (opcionModificar == 1) {

					System.out.print("Por favor, ingrese el nuevo email: ");
					String emailNuevo = sc.next();
					EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsersDB");
					EntityManager entityManager = factory.createEntityManager();

					entityManager.getTransaction().begin();

					users existingUser = new users();
					existingUser.setId(IdModificar);
					existingUser.setNombre(nombreModificar);
					existingUser.setApellido(apellidoModificar);
					existingUser.setEmail(emailNuevo);
					existingUser.setTelefono(telefonoModificar);

					entityManager.merge(existingUser);

					entityManager.getTransaction().commit();

				} else if (opcionModificar == 2) {

					System.out.print("Por favor, ingrese el nuevo número de teléfono: ");
					int telefonoNuevo = sc.nextInt();
					EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsersDB");
					EntityManager entityManager = factory.createEntityManager();

					entityManager.getTransaction().begin();

					users existingUser = new users();
					existingUser.setId(IdModificar);
					existingUser.setNombre(nombreModificar);
					existingUser.setApellido(apellidoModificar);
					existingUser.setEmail(emailModificar);
					existingUser.setTelefono(telefonoNuevo);

					entityManager.merge(existingUser);

					entityManager.getTransaction().commit();

				}

				contadorModificar++;
				deshabilitar = false;

			} else if (opcion == 3) {

				System.out.println("\nPor favor, introduzca los siguientes datos:");
				System.out.print("Introduzca el nombre: ");
				String nombreBuscar = sc.next();
				System.out.print("Introduzca el email: ");
				String emailBuscar = sc.next();

				EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsersDB");
				EntityManager entityManager = factory.createEntityManager();
				String sql = "select u from users u where u.nombre='" + nombreBuscar + "' and u.email='" + emailBuscar
						+ "'";
				entityManager.getTransaction().begin();

				Query query = entityManager.createQuery(sql);

				List<?> lista = query.getResultList();
				
				System.out.println("\nEste es el contacto que has buscado: ");
				for (int i = 0; i < lista.size(); i++) {
					
					users mostrar = (users) lista.get(i);
					System.out.println(mostrar);
				}
				
				entityManager.getTransaction().commit();
				entityManager.close();
				factory.close();

				contadorBuscar++;
				deshabilitar = false;
				
			} else if (opcion == 4) {

				System.out.println("\nPara borrar un contacto, introduzca el siguiente dato: ");
				System.out.print("Introduzca el Id: ");
				int id = sc.nextInt();

				EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsersDB");
				EntityManager entityManager = factory.createEntityManager();

				entityManager.getTransaction().begin();

				users reference = entityManager.getReference(users.class, id);
				entityManager.remove(reference);
				entityManager.getTransaction().commit();
				
				entityManager.close();
				factory.close();
				
				System.out.println("Contacto eliminado.");

				contadorEliminar++;
				deshabilitar = false;

			} else if (opcion == 5) {

				System.out.println("\nEstos son sus movimientos: ");
				System.out.println("Has realizado un total de " + contadorAlta + " altas.");
				System.out.println("Has realizado un total de " + contadorModificar + " modificaciones.");
				System.out.println("Has realizado un total de " + contadorBuscar + " busquedas.");
				System.out.println("Has realizado un total de " + contadorEliminar + " eliminaciones.");
				System.out.println("Has realizado un total de " + contadorVerContactos + " consultas.");

				deshabilitar = false;
			} else if (opcion == 6) {

				EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsersDB");
				EntityManager entityManager = factory.createEntityManager();
				String jpql = "From users";

				Query query = entityManager.createQuery(jpql);

				List<?> lista = query.getResultList();
				System.out.println("\n");

				System.out.println("\nEste es el listado de todos los contactos:");
				for (int i = 0; i < lista.size(); i++) {

					users mostrar = (users) lista.get(i);

					System.out.println(mostrar);
				}
				entityManager.close();
				factory.close();

				contadorBuscar++;
				deshabilitar = false;

			} else if (opcion == 0) {

				System.out.println("Saliendo de la agenda...");

			} else {

				System.out.println("Opción incorrecta.");
			}

		} while (opcion != 0);

		sc.close();
	}

}
