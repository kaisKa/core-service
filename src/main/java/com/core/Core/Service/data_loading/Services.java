package com.core.Core.Service.data_loading;

import com.core.Core.Service.data_loading.entity.ServiceConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Services {

    List<ServiceConfiguration> services;
}
