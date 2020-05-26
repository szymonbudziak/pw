#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <mpi.h>
#define AMOUNT 10000000

struct person_t
{
    char name[20];
    char surname[20];
    char pesel[20];
    int age;
    double height;
    double weight;  
};
 
int main(int argc, char* argv[])
{
    MPI_Init(&argc, &argv);
    int position = 0;
 
    MPI_Datatype person_type;
    int lengths[6] = { 20, 20, 20, 1, 1, 1 };
    const MPI_Aint displacements[6] = { 0, sizeof(char)*20, sizeof(char)*40, sizeof(char)*60,
                                        sizeof(char)*60 + sizeof(int), sizeof(char)*60 + sizeof(int) + sizeof(double)};
    MPI_Datatype types[6] = {MPI_CHAR, MPI_CHAR, MPI_CHAR, MPI_INT, MPI_DOUBLE, MPI_DOUBLE};
    MPI_Type_create_struct(6, lengths, displacements, types, &person_type);
    MPI_Type_commit(&person_type);
 
    int my_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

    struct person_t send;
    send.age = 20;
    send.height = 1.83;
    send.weight = 74.55;
    strncpy(send.name, "Abcd", 19);
    send.name[19] = '\0';
    strncpy(send.surname, "Efgh", 19);
    send.surname[19] = '\0';
    strncpy(send.pesel, "Ijkl", 19);
    send.pesel[19] = '\0'; 

    int buff_size;
    void *buffer;

    MPI_Pack_size(AMOUNT, person_type, MPI_COMM_WORLD, &buff_size); //wersja pakowana
    buffer = (char *) malloc((unsigned) buff_size);
      
        if(my_rank == 0) {
            int buffer_attached_size = MPI_BSEND_OVERHEAD + (sizeof(int) + sizeof(double)*2 + sizeof(char)*60) * AMOUNT;
            char* buffer_attached = (char*)malloc(buffer_attached_size);
            
            printf("Wysyłanie rozpoczete %lf\n", MPI_Wtime());
            for (int i = 0; i < AMOUNT; i++) {
/*wersja bez pakowania*/
                //MPI_Send(&send, 1, person_type,  1, 13, MPI_COMM_WORLD);
                //MPI_Ssend(&send, 1, person_type,  1, 13, MPI_COMM_WORLD);                
                //MPI_Buffer_attach(buffer_attached, buffer_attached_size);
                //MPI_Bsend(buffer_attached, 1, person_type, 1, 13, MPI_COMM_WORLD);
                //MPI_Buffer_detach(&buffer_attached, &buffer_attached_size);
                                                                                /*wersja bez pakowania*/
                MPI_Pack(&send, 1, person_type, buffer, buff_size, &position, MPI_COMM_WORLD);
            }
/*wersja pakowana:*/
            //MPI_Send(buffer, position, MPI_PACKED, 1, 13, MPI_COMM_WORLD);
            //MPI_Ssend(buffer, position, MPI_PACKED, 1, 13, MPI_COMM_WORLD);
            MPI_Buffer_attach(buffer_attached, buffer_attached_size);
            MPI_Bsend(buffer, position, MPI_PACKED, 1, 13, MPI_COMM_WORLD);
                                                                                 /*wersja pakowana:*/             
        }
        else if(my_rank == 1){
            struct person_t received;
            MPI_Status status;
            MPI_Recv(buffer, buff_size, MPI_PACKED, 0, 13, MPI_COMM_WORLD, &status);//pakowana
            for (int i = 0; i < AMOUNT; i++) {
                MPI_Unpack(buffer, buff_size, &position, &received, 1, person_type, MPI_COMM_WORLD);//pakowana
                //MPI_Recv(&received, 1, person_type, 0, 13, MPI_COMM_WORLD, &status); //bez pakowania
            }
            printf("Odbieranie zakonczone %lf\n", MPI_Wtime());
        }
 
    MPI_Finalize();
    return EXIT_SUCCESS;
}