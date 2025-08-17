package com.thinkIndia.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.TeamMember;
import com.thinkIndia.backend.repositories.TeamMemberRepo;
import com.thinkIndia.backend.services.TeamMemberService;

import jakarta.transaction.Transactional;

@Service
public class TeamMemberServiceImpl implements TeamMemberService{
    @Autowired
    private TeamMemberRepo teamMemberRepository;

    @Override
    @Transactional
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

    @Override
    public List<TeamMember> getMemberByPosition(String position) {
        List<TeamMember> memberList = teamMemberRepository.getMemberByPosition(position);
        return memberList;
    }

    @Override
    public List<TeamMember> getMembers() {
        return teamMemberRepository.findAll();
    }
}
