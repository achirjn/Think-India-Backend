package com.thinkIndia.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.TeamMember;
import com.thinkIndia.backend.repositories.TeamMemberRepo;
import com.thinkIndia.backend.services.TeamMemberService;

@Service
public class TeamMemberServiceImpl implements TeamMemberService{
    @Autowired
    private TeamMemberRepo teamMemberRepository;

    @Override
    public List<TeamMember> getByCommittee(String committee) {
        return teamMemberRepository.findByCommittee(committee);
    }

    @Override
    public void deleteMember(int id) {
        teamMemberRepository.deleteById(id);
    }

    @Override
    public TeamMember saveMember(TeamMember member) {
        return teamMemberRepository.save(member);
    }

    @Override
    public Optional<TeamMember> getById(int id){
        return teamMemberRepository.findById(id);
    }
}
