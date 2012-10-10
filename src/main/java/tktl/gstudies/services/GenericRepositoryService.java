package tktl.gstudies.services;

import org.springframework.data.jpa.repository.JpaRepository;

public class GenericRepositoryService<T> {

    protected JpaRepository<T, Integer> repository;

    public void setRepository(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }
    
    public T save(T t){
        return this.repository.save(t);
    }
}
